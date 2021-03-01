package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.AccountRole;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 用户角色表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = AccountRole.TABLE_NAME)
public interface IAccountRoleService extends IBaseService<AccountRole> {

}

