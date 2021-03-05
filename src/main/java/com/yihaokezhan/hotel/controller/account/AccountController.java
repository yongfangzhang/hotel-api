package com.yihaokezhan.hotel.controller.account;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.CustomerChannel;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.Assert;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.form.RegisterForm;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.service.IAccountService;
import com.yihaokezhan.hotel.module.service.IUserService;
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

    @Autowired
    private IUserService userService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "分页查看账号列表 %s", params = "#params")
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看账号列表 %s", params = "#params")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看账号 %s", params = "#params")
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(accountService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_GET)
    @SysLog(operation = Operation.RETRIEVE, description = "查看账号详情 %s", params = "#uuid")
    public R get(@PathVariable String uuid) {
        return R.ok().data(accountService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_CREATE)
    @SysLog(operation = Operation.CREATE, description = "创建账号 %s", params = "#form")
    @Transactional(rollbackFor = Exception.class)
    public R create(@Validated(AddGroup.class) @RequestBody RegisterForm form) {
        Assert.notNull(form.getUser(), "用户信息不能为空");
        form.getUser().setChannel(CustomerChannel.BACKEND.getValue());
        User user = userService.mCreate(form.getUser());
        form.getAccount().setUserUuid(user.getUuid());
        form.getAccount().setTenantUuid(user.getTenantUuid());
        return R.ok().data(accountService.register(form));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_UPDATE)
    @SysLog(operation = Operation.UPDATE, description = "更新账号 %s", params = "#entity")
    @Transactional(rollbackFor = Exception.class)
    public R update(@Validated(UpdateGroup.class) @RequestBody Account entity) {
        return R.ok().data(accountService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ACCOUNT_DELETE)
    @SysLog(operation = Operation.DELETE, linked = "#uuid", description = "删除账号 %s",
            params = "#uuid")
    @Transactional(rollbackFor = Exception.class)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(accountService.mDelete(uuid));
    }
}
