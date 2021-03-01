package com.yihaokezhan.hotel.controller;

import java.util.Map;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.service.IAccountService;
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
 * 登录账号表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountService.mPage(params));
    }

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountService.mList(params));
    }

    @GetMapping("/one")
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountService.mOne(params));
    }

    @GetMapping("/{uuid}")
    public R get(@PathVariable String uuid) {
        return R.ok().data(accountService.mGet(uuid));
    }

    @PostMapping("")
    @Transactional(rollbackFor = Exception.class)
    public R create(@Validated(AddGroup.class) @RequestBody Account entity) {
        return R.ok().data(accountService.mCreate(entity));
    }

    @PutMapping("")
    public R update(@Validated(UpdateGroup.class) @RequestBody Account entity) {
        return R.ok().data(accountService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(accountService.mDelete(uuid));
    }
}
