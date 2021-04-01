package com.yihaokezhan.hotel.controller.apartment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.ApartmentState;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.MapUtils;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.Assert;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.module.entity.Apartment;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.service.IApartmentService;
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
 * 公寓表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/apartment")
public class ApartmentController {

    @Autowired
    private IApartmentService apartmentService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IOrderService orderService;

    // @Autowired
    // private IOrderItemService orderItemService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_GET)
    public R page(@RequestParam Map<String, Object> params) {
        Pager<Apartment> data = apartmentService.mPage(params);
        this.fillApartmentReport(params, data.getList());
        return R.ok().data(data);
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(apartmentService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_GET)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(apartmentService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_GET)
    public R get(@PathVariable String uuid) {
        return R.ok().data(apartmentService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_CREATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "创建公寓 %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody Apartment entity) {
        entity.removeIgnores();
        return R.ok().data(apartmentService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "更新公寓 %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody Apartment entity) {
        entity.removeIgnores();

        if (StringUtils.isBlank(entity.getUuid())) {
            return R.error("公寓不存在");
        }

        if (ApartmentState.FORBIDDEN.getValue().equals(entity.getState())) {

            Apartment apartment = apartmentService.mGet(entity.getUuid());

            Assert.notNull(apartment, "公寓不存在");
            List<Room> rooms = roomService.mList(M.m().put("apartmentUuid", entity.getUuid()));
            long count = rooms.stream().filter(room -> StringUtils.isNotBlank(room.getOrderItemUuid())).count();

            Assert.state(count == 0, "公寓存在" + count + "个房间正在入住中， 不允许禁用");
        }

        return R.ok().data(apartmentService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_APARTMENT_DELETE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.DELETE, description = "删除公寓 %s", params = "#uuid")
    public R delete(@PathVariable String uuid) {

        Apartment apartment = apartmentService.mGet(uuid);

        Assert.notNull(apartment, "公寓不存在");
        List<Room> rooms = roomService.mList(M.m().put("apartmentUuid", uuid));
        long count = rooms.stream().filter(room -> StringUtils.isNotBlank(room.getOrderItemUuid())).count();

        Assert.state(count == 0, "公寓存在" + count + "个房间正在入住中， 不允许删除");

        return R.ok().data(apartmentService.mDelete(uuid));
    }

    private void fillApartmentReport(Map<String, Object> params, List<Apartment> records) {
        if (!MapUtils.getBoolean(params, "report") || CollectionUtils.isEmpty(records)) {
            return;
        }

        M orderParams = M.m();

        orderParams.put("apartmentUuids", records.stream().map(record->record.getUuid()).collect(Collectors.toList()));
        orderParams.put("operatorUuid", params.get("operatorUuid"));
        orderParams.put("channel", params.get("channel"));
        // orderParams.put("report", params.get("report"));
        // orderParams.put("name", params.get("name"));
        orderParams.put("createdAtStart", params.get("orderCreatedAtStart"));
        orderParams.put("createdAtStop", params.get("orderCreatedAtStop"));
        orderParams.put("createdTimeAtStart", params.get("orderShiftStart"));
        orderParams.put("createdTimeAtStop", params.get("orderShiftStop"));
        // orderParams.put("vstate", 1);

        orderService.attachListItems(records, orderParams, Order::getApartmentUuid, (record, orderMap) -> {
            record.setSaleTimes(0);
            record.setIncome(BigDecimal.ZERO);
            if (orderMap == null || orderMap.isEmpty()) {
                return;
            }
            List<Order> orders = orderMap.get(record.getUuid());
            if (CollectionUtils.isEmpty(orders)) {
                return;
            }
            orders.forEach(order -> {
                record.setSaleTimes(record.getSaleTimes() + 1);
                record.setIncome(record.getIncome().add(order.getPaidPrice()));
            });
        });
    }
}
