package com.yihaokezhan.hotel.controller.order;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.service.IOrderService;
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
 * 订单表 前端控制器
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
    public R create(@Validated(AddGroup.class) @RequestBody Order entity) {
        return R.ok().data(orderService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    public R update(@Validated(UpdateGroup.class) @RequestBody Order entity) {
        return R.ok().data(orderService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_DELETE)
    @Transactional(rollbackFor = Exception.class)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(orderService.mDelete(uuid));
    }
}
