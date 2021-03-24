package com.yihaokezhan.hotel.controller.room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.enums.OrderState;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.MapUtils;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.Assert;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.model.RoomPrice;
import com.yihaokezhan.hotel.module.entity.OrderItem;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.service.IOrderItemService;
import com.yihaokezhan.hotel.module.service.IOrderService;
import com.yihaokezhan.hotel.module.service.IRoomService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 房间表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/room")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R page(@RequestParam Map<String, Object> params) {
        Pager<Room> data = roomService.mPage(params);
        this.join(data.getList());
        this.fillRoomReport(params, data.getList());
        return R.ok().data(data);
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R list(@RequestParam Map<String, Object> params) {
        List<Room> data = roomService.mList(params);
        this.join(data);
        return R.ok().data(data);
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R one(@RequestParam Map<String, Object> params) {
        Room data = roomService.mOne(params);
        this.join(data);
        return R.ok().data(data);
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R get(@PathVariable String uuid) {
        Room data = roomService.mGet(uuid);
        this.join(data);
        return R.ok().data(data);
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions({ Constant.PERM_ROOM_CREATE, Constant.PERM_ROOM_PRICE_UPDATE })
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "创建房间 %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody Room entity) {
        entity.removeCreateIgnores();
        return R.ok().data(roomService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "更新房间 %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody Room entity) {

        Room originRoom = roomService.mGet(entity.getUuid());

        if (originRoom == null) {
            return R.ok().data(originRoom);
        }

        String orderItemUuid = originRoom.getOrderItemUuid();

        if (RoomState.FORBIDDEN.getValue().equals(entity.getState()) && StringUtils.isNotBlank(orderItemUuid)) {
            return R.error("房间正在入住中， 不允许禁用");
        }

        entity.removeUpdateIgnores();

        if (StringUtils.isNotBlank(orderItemUuid) && (RoomState.EMPTY_CLEAN.getValue().equals(entity.getState())
                || RoomState.EMPTY_DARTY.getValue().equals(entity.getState()))) {
            // 有入住单并且，将入住单退房， 把订单改成已完成
            this.join(originRoom);
            if (OrderState.FINISHED.getValue().compareTo(originRoom.getRelatedOrder().getState()) > 0) {
                originRoom.getRelatedOrder().setState(OrderState.FINISHED.getValue());
                orderService.mUpdate(originRoom.getRelatedOrder());
            }
        }

        entity = roomService.mUpdate(entity);

        return R.ok().data(entity);
    }

    @PutMapping("/{uuid}/price")
    @JsonView(V.S.class)
    @RequiresPermissions({ Constant.PERM_ROOM_CREATE, Constant.PERM_ROOM_PRICE_UPDATE })
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "修改房间 %s 价格 %s", params = { "#uuid", "#entity" })
    public R updatePrice(@PathVariable String uuid, @Validated(UpdateGroup.class) @RequestBody RoomPrice entity) {

        Room room = roomService.mGet(uuid);
        if (room == null) {
            return R.error("房间不存在");
        }

        List<RoomPrice> prices = room.getPrices();

        if (CollectionUtils.isEmpty(prices)) {
            prices = new ArrayList<>();
        }

        for (RoomPrice price : prices) {
            if (price.getType().equals(entity.getType())) {
                price.setPrice(entity.getPrice());
            }
        }

        return R.ok().data(roomService.mUpdate(room));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_DELETE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.DELETE, description = "删除房间 %s", params = "#uuid")
    public R delete(@PathVariable String uuid) {
        Room room = roomService.mGet(uuid);
        Assert.notNull(room, "房间不存在");
        Assert.state(StringUtils.isBlank(room.getOrderItemUuid()), "房间正在入住中， 不允许删除");
        return R.ok().data(roomService.mDelete(uuid));
    }

    private void join(Room room) {
        if (room == null || StringUtils.isBlank(room.getOrderItemUuid())) {
            return;
        }
        orderItemService.attachOne(room, room.getOrderItemUuid(), (record, item) -> {
            record.setRelatedOrderItem(item);
        });
        orderService.attachOne(room, room.getRelatedOrderItem().getOrderUuid(), (record, item) -> {
            record.setRelatedOrder(item);
        });
    }

    private void join(List<Room> rooms) {
        if (CollectionUtils.isEmpty(rooms)) {
            return;
        }
        List<Room> orderedRooms = rooms.stream().filter(room -> StringUtils.isNotBlank(room.getOrderItemUuid()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(orderedRooms)) {
            return;
        }

        List<String> orderItemUuids = orderedRooms.stream().map(room -> room.getOrderItemUuid())
                .collect(Collectors.toList());

        orderItemService.attachList(orderedRooms, orderItemUuids, (record, map) -> {
            record.setRelatedOrderItem(map.get(record.getOrderItemUuid()));
        });

        List<String> orderUuids = orderedRooms.stream().map(room -> room.getRelatedOrderItem().getOrderUuid())
                .distinct().collect(Collectors.toList());

        orderService.attachList(orderedRooms, orderUuids, (record, map) -> {
            record.setRelatedOrder(map.get(record.getRelatedOrderItem().getOrderUuid()));
        });
    }

    private void fillRoomReport(Map<String, Object> params, List<Room> records) {
        if (!MapUtils.getBoolean(params, "report")) {
            return;
        }
        M orderItemParams = M.m();

        orderItemParams.put("roomUuids", records.stream().map(record -> record.getUuid()).collect(Collectors.toList()));
        orderItemParams.put("operatorUuid", params.get("operatorUuid"));
        orderItemParams.put("apartmentUuid", params.get("apartmentUuid"));
        orderItemParams.put("channel", params.get("channel"));
        // orderItemParams.put("report", params.get("report"));
        // orderItemParams.put("name", params.get("name"));
        orderItemParams.put("createdAtStart", params.get("orderCreatedAtStart"));
        orderItemParams.put("createdAtStop", params.get("orderCreatedAtStop"));
        // orderItemParams.put("vstate", 1);

        boolean filterChannel = StringUtils.isNotBlank(MapUtils.getString(params, "channel"));

        orderItemService.attachListItems(records, orderItemParams, OrderItem::getRoomUuid, (record, orderItemMap) -> {
            record.setSaleTimes(0);
            record.setIncome(BigDecimal.ZERO);
            if (orderItemMap == null || orderItemMap.isEmpty()) {
                return;
            }
            List<OrderItem> orderItems = orderItemMap.get(record.getUuid());
            if (CollectionUtils.isEmpty(orderItems)) {
                return;
            }

            if (filterChannel) {

                List<String> orderUuids = orderItems.stream().map(item -> item.getOrderUuid()).distinct()
                        .collect(Collectors.toList());

                List<String> validOrderUuids = orderService
                        .mList(M.m().put("uuids", orderUuids).put("channel", params.get("channel"))).stream()
                        .map(order -> order.getUuid()).collect(Collectors.toList());

                orderItems = orderItems.stream().filter(item -> validOrderUuids.contains(item.getOrderUuid()))
                        .collect(Collectors.toList());

            }

            orderItems.forEach(orderItem -> {
                record.setSaleTimes(record.getSaleTimes() + 1);
                record.setIncome(record.getIncome().add(orderItem.getPaidPrice()));
            });
        });
    }
}
