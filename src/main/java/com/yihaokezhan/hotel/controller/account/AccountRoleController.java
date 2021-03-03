package com.yihaokezhan.hotel.controller.account;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
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
 * 用户角色表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/account/role")
public class AccountRoleController {

    @Autowired
    private IAccountRoleService accountRoleService;


    @GetMapping("/page")
    @JsonView(V.S.class)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountRoleService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountRoleService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountRoleService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    public R get(@PathVariable String uuid) {
        return R.ok().data(accountRoleService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    public R create(@Validated(AddGroup.class) @RequestBody AccountRole entity) {
        return R.ok().data(accountRoleService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    public R update(@Validated(UpdateGroup.class) @RequestBody AccountRole entity) {
        return R.ok().data(accountRoleService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(accountRoleService.mDelete(uuid));
    }
}
