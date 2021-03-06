package com.yihaokezhan.hotel.controller.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.captcha.service.ICaptchaService;
import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.annotation.Dev;
import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.AccountType;
import com.yihaokezhan.hotel.common.enums.AspectPos;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.form.LoginForm;
import com.yihaokezhan.hotel.form.RegisterForm;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.service.IAccountService;
import com.yihaokezhan.hotel.module.service.ITenantService;
import com.yihaokezhan.hotel.module.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
    private ICaptchaService captchaService;

    @Autowired
    private ShiroUtils shiroUtils;

    @GetMapping("/info")
    @JsonView(V.S.class)
    public R info(@LoginUser TokenUser tokenUser) {
        return R.ok().data(accountService.mGet(tokenUser.getUuid()));
    }

    @Dev
    @PostMapping("/register")
    @JsonView(V.S.class)
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
        return R.ok().data(accountService.register(form));
    }

    @Annc
    @PostMapping("/login")
    @SysLog(operation = Operation.LOGIN, linked = "#form.getAccount()", description = "登录")
    @JsonView(V.S.class)
    public R login(@Validated @RequestBody LoginForm form) {
        captchaService.validateCaptcha(form.getCaptchaSid(), form.getCaptchaCode());
        Account account = accountService.login(form);
        if (account == null) {
            throw new RRException("登录失败");
        }
        shiroUtils.login(account.getUuid(), account.getToken());
        return R.ok().data(account);
    }


    @Annc
    @GetMapping("/login/11eb7a9d0c449fc68e30a1e166e56231")
    public String autoLogin() {
        try {
            LoginForm form = new LoginForm();
            form.setAccount("15969735516");
            form.setPassword("a1234567");
            form.setType(AccountType.HOTEL_ADMIN.getValue());
            form.setDevice("Device");
            form.setUserAgent("UA");

            Account account = accountService.login(form);
            if (account == null) {
                throw new RRException("登录失败");
            }
            shiroUtils.login(account.getUuid(), account.getToken());

            String html = "";
            html += "<!DOCTYPE html>";
            html += "<html lang=\"en\">";
            html += "<head>";
            html += "<meta charset=\"UTF-8\">";
            html += "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">";
            html += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
            html += "<title>Redirect</title>";
            html += "</head>";
            html += "<body>";
            html += "<script>";
            html += "document.cookie = \"Access-Token=" + account.getToken() + "; path=/\";";
            html += "location.href = 'https://hotel.helisoft.cn/panda/dashboard/index';";
            html += "</script>";
            html += "</body>";
            html += "</html>";
            return html;
        } catch (Exception e) {
            log.error("whats' wrong?", e);
            return ErrorCode.NOT_FOUND.getCode().toString();
        }
    }



    @Annc
    @RequestMapping("/logout")
    @SysLog(operation = Operation.LOGOUT, linked = "#user.getAccount()", description = "退出",
            position = AspectPos.BEFORE)
    @JsonView(V.S.class)
    public R logout(@LoginUser(required = false) TokenUser tokenUser) {
        if (tokenUser == null) {
            return R.ok();
        }
        tokenUtils.expireToken(tokenUser.getToken());
        return R.ok();
    }
}
