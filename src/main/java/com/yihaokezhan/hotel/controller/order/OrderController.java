package com.yihaokezhan.hotel.controller.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.enums.OrderState;
import com.yihaokezhan.hotel.common.enums.OrderType;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.Assert;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.OrderItem;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.service.IApartmentService;
import com.yihaokezhan.hotel.module.service.IOrderService;
import com.yihaokezhan.hotel.module.service.IRoomService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
 * ????????? ???????????????
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IApartmentService apartmentService;

    @Autowired
    private IRoomService roomService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(orderService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(orderService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(orderService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R get(@PathVariable String uuid) {
        return R.ok().data(orderService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_CREATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "???????????? %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody Order entity, @LoginUser TokenUser tokenUser) {

        // ??????????????????
        entity.removeCreateIgnores();
        beforeCreateOrder(entity, tokenUser);
        entity = orderService.mCreate(entity);

        // ??????????????????????????????

        apartmentService.onOrderCreated(entity);

        return R.ok().data(entity);
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "???????????? %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody Order entity, @LoginUser TokenUser tokenUser) {
        Order originOrder = orderService.mGet(entity.getUuid());

        if (entity.isRenew()) {
            if (CollectionUtils.isEmpty(entity.getItems())) {
                return R.error("???????????????????????????");
            }
            // ??????, ????????????????????????
            originOrder.setState(OrderState.FINISHED.getValue());
            orderService.mUpdate(originOrder);
            // ??????????????????????????????

            List<Room> originRooms = entity.getItems().stream()
                    .filter(item -> StringUtils.isNotBlank(item.getRoomUuid())).map(item -> {
                        Room room = new Room();
                        room.setUuid(item.getRoomUuid());
                        room.setState(RoomState.EMPTY_DARTY.getValue());
                        return room;
                    }).collect(Collectors.toList());
            roomService.mBatchUpdate(originRooms);

            // ??????????????????
            entity.removeCreateIgnores();

            beforeCreateOrder(entity, tokenUser);

            entity = orderService.mCreate(entity);
            // ??????????????????????????????
            apartmentService.onOrderCreated(entity);
            return R.ok().data(entity);
        }
        entity.removeUpdateIgnores();
        return R.ok().data(orderService.mUpdate(entity));

    }

    private void beforeCreateOrder(Order entity, TokenUser tokenUser) {
        entity.setOperatorUuid(tokenUser.getUserUuid());
        entity.setAccountType(tokenUser.getAccountType());
        entity.setType(OrderType.LIVE_IN.getValue());

        List<OrderItem> items = entity.getItems();

        Assert.state(CollectionUtils.isNotEmpty(items), "?????????????????????");

        entity.setOriginalPrice(BigDecimal.ZERO);
        entity.setPaidPrice(BigDecimal.ZERO);

        for (OrderItem item : items) {
            item.removeCreateIgnores();
            Assert.isTrue(BigDecimal.ZERO.compareTo(item.getOriginalPrice()) <= 0, "??????????????????");
            Assert.isTrue(BigDecimal.ZERO.compareTo(item.getPaidPrice()) <= 0, "??????????????????");

            entity.setOriginalPrice(entity.getOriginalPrice().add(item.getOriginalPrice()));
            entity.setPaidPrice(entity.getPaidPrice().add(item.getPaidPrice()));
        }
    }

    // @DeleteMapping("/{uuid}")
    // @JsonView(V.S.class)
    // @RequiresPermissions(Constant.PERM_ORDER_DELETE)
    // @Transactional(rollbackFor = Exception.class)
    // @SysLog(operation = Operation.DELETE, description = "???????????? %s", params =
    // "#uuid")
    // public R delete(@PathVariable String uuid) {
    // return R.ok().data(orderService.mDelete(uuid));
    // }
}
