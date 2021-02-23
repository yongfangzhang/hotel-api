package com.yihaokezhan.hotel.controller;


import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Apartment;
import com.yihaokezhan.hotel.module.service.IApartmentService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 公寓表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/hotel/apartment")
public class ApartmentController {

    @Autowired
    private IApartmentService apartmentService;

    @GetMapping("/{uuid}")
    @RequiresPermissions("")
    public R get(@PathVariable String uuid) {
        return R.ok().data(apartmentService.getById(uuid));
    }

    @PostMapping("")
    public R create(@Validated(AddGroup.class) @RequestBody Apartment entity) {
        return R.ok().data(apartmentService.save(entity));
    }

    @PutMapping("")
    public R update(@Validated(UpdateGroup.class) @RequestBody Apartment entity) {
        return R.ok().data(apartmentService.updateById(entity));
    }

    @DeleteMapping("/{uuid}")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(apartmentService.removeById(uuid));
    }

}

