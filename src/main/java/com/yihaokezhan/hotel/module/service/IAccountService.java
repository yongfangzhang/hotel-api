package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Account;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 登录账号表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Account.TABLE_NAME)
public interface IAccountService extends IBaseService<Account> {

}

