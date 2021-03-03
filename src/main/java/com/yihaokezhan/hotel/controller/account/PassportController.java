package com.yihaokezhan.hotel.controller.account;

import com.yihaokezhan.hotel.captcha.CaptchaService;
import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.annotation.Dev;
import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.form.LoginForm;
import com.yihaokezhan.hotel.form.RegisterForm;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.service.IAccountService;
import com.yihaokezhan.hotel.module.service.ITenantService;
import com.yihaokezhan.hotel.module.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Passport 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-03
 */
@RestController
@RequestMapping("/hotel/account/passport")
public class PassportController {

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CaptchaService captchaService;

    @Dev
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public R register(@Validated @RequestBody RegisterForm form) {
        if (form.getTenant() != null) {
            Tenant tenant = tenantService.mCreate(form.getTenant());
            User user = new User();
            user.setTenantUuid(tenant.getUuid());
            form.setUser(user);
        }
        if (form.getUser() != null) {
            User user = userService.mCreate(form.getUser());
            form.getAccount().setUserUuid(user.getUuid());
            form.getAccount().setTenantUuid(user.getTenantUuid());
        }
        return R.ok().data(accountService.mCreate(form.getAccount()));
    }

    @Annc
    @PostMapping("/login")
    public R login(@Validated @RequestBody LoginForm form) {
        captchaService.validateCaptcha(form.getCaptchaSid(), form.getCaptchaCode());
        return R.ok().data(accountService.login(form));
    }

    @Annc
    @RequestMapping("/logout")
    public R logout(@LoginUser(required = false) TokenUser tokenUser) {
        if (tokenUser == null) {
            return R.ok();
        }
        tokenUtils.expireToken(tokenUser.getToken());
        return R.ok();
    }
}
