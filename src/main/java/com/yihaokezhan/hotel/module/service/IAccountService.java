package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.form.LoginForm;
import com.yihaokezhan.hotel.form.RegisterForm;
import com.yihaokezhan.hotel.module.entity.Account;


/**
 * <p>
 * 登录账号表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface IAccountService extends IBaseService<Account> {

    Account register(RegisterForm form);

    Account login(LoginForm form);

}

