package com.yihaokezhan.hotel.controller.route;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.service.IRouteService;

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
 * 路由表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/route")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_GET)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(routeService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(routeService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_GET)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(routeService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_GET)
    public R get(@PathVariable String uuid) {
        return R.ok().data(routeService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_CREATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "创建路由 %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody Route entity) {
        return R.ok().data(routeService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "更新路由 %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody Route entity) {
        return R.ok().data(routeService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROUTE_DELETE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.DELETE, description = "删除路由 %s", params = "#uuid")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(routeService.mDelete(uuid));
    }
}
