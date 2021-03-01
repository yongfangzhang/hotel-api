package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Role;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 角色表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Role.TABLE_NAME)
public interface IRoleService extends IBaseService<Role> {

}

