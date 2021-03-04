package com.yihaokezhan.hotel.controller.role;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.RoleRoute;
import com.yihaokezhan.hotel.module.service.IRoleRouteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 角色路由表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/role/route")
public class RoleRouteController {

    @Autowired
    private IRoleRouteService roleRouteService;


    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_GET)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleRouteService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleRouteService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_GET)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleRouteService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_GET)
    public R get(@PathVariable String uuid) {
        return R.ok().data(roleRouteService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_CREATE)
    public R create(@Validated(AddGroup.class) @RequestBody RoleRoute entity) {
        return R.ok().data(roleRouteService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_UPDATE)
    public R update(@Validated(UpdateGroup.class) @RequestBody RoleRoute entity) {
        return R.ok().data(roleRouteService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_ROUTE_DELETE)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(roleRouteService.mDelete(uuid));
    }
}
