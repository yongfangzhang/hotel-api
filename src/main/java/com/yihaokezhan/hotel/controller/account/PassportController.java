package com.yihaokezhan.hotel.controller.account;

import com.yihaokezhan.hotel.captcha.CaptchaService;
import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.form.LoginForm;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IAccountService accountService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CaptchaService captchaService;

    // @Annc
    // @PostMapping("/register")
    // @Transactional(rollbackFor = Exception.class)
    // public R register(@Validated(AddGroup.class) @RequestBody Account entity) {
    // return R.ok().data(accountService.mCreate(entity));
    // }

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
