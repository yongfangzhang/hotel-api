package com.yihaokezhan.hotel.controller;


import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.service.IUserService;
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
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/hotel/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{uuid}")
    public R get(@PathVariable String uuid) {
        return R.ok().data(userService.mGet(uuid));
    }

    @GetMapping("/info")
    public R getInfo(@LoginUser User user) {
        return R.ok().data(user);
    }

    @PostMapping("")
    public R create(@Validated(AddGroup.class) @RequestBody User entity) {
        return R.ok().data(userService.mCreate(entity));
    }

    @PutMapping("")
    public R update(@Validated(UpdateGroup.class) @RequestBody User entity) {
        return R.ok().data(userService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(userService.mDelete(uuid));
    }
}

