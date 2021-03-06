package com.yihaokezhan.hotel.controller.tenant;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.Dev;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.service.ITenantService;

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
 * 租户表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/tenant")
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "分页查看租户列表 %s", params = "#params")
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(tenantService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看租户列表 %s", params = "#params")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(tenantService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看租户 %s", params = "#params")
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(tenantService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看租户详情 %s", params = "#uuid")
    public R get(@PathVariable String uuid) {
        return R.ok().data(tenantService.mGet(uuid));
    }

    @Dev
    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_CREATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "创建租户 %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody Tenant entity) {
        return R.ok().data(tenantService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "更新租户 %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody Tenant entity) {
        return R.ok().data(tenantService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_TENANT_DELETE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.DELETE, description = "删除租户 %s", params = "#uuid")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(tenantService.mDelete(uuid));
    }
}
