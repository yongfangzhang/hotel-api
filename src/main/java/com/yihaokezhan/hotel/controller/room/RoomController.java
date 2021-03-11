package com.yihaokezhan.hotel.controller.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.model.RoomPrice;
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
        this.attachOrder(data.getList());
        return R.ok().data(data);
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R list(@RequestParam Map<String, Object> params) {
        List<Room> data = roomService.mList(params);
        this.attachOrder(data);
        return R.ok().data(data);
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R one(@RequestParam Map<String, Object> params) {
        Room data = roomService.mOne(params);
        this.attachOrder(data);
        return R.ok().data(data);
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROOM_GET)
    public R get(@PathVariable String uuid) {
        Room data = roomService.mGet(uuid);
        this.attachOrder(data);
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
        entity.removeUpdateIgnores();
        return R.ok().data(roomService.mUpdate(entity));
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
        return R.ok().data(roomService.mDelete(uuid));
    }

    private void attachOrder(Room room) {
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

    private void attachOrder(List<Room> rooms) {
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
}
