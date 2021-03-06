package com.yihaokezhan.hotel.controller.role;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.service.IRoleService;

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
 * 角色表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "分页查看角色列表 %s", params = "#params")
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看角色列表 %s", params = "#params")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看角色 %s", params = "#params")
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看角色详情 %s", params = "#uuid")
    public R get(@PathVariable String uuid) {
        return R.ok().data(roleService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_CREATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "创建角色 %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody Role entity) {
        return R.ok().data(roleService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "更新角色 %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody Role entity) {
        return R.ok().data(roleService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ROLE_DELETE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.DELETE, description = "删除角色 %s", params = "#uuid")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(roleService.mDelete(uuid));
    }
}
