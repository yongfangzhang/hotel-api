package com.yihaokezhan.hotel.controller;


import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/hotel/tenant")
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @GetMapping("/{uuid}")
    public R get(@PathVariable String uuid) {
        return R.ok().data(tenantService.getById(uuid));
    }

    @PostMapping("")
    public R create(@Validated(AddGroup.class) @RequestBody Tenant entity) {
        return R.ok().data(tenantService.save(entity));
    }

    @PutMapping("")
    public R update(@Validated(UpdateGroup.class) @RequestBody Tenant entity) {
        return R.ok().data(tenantService.updateById(entity));
    }

    @DeleteMapping("/{uuid}")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(tenantService.removeById(uuid));
    }
}

