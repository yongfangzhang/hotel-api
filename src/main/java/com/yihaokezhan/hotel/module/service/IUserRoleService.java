package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.UserRole;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "UserRole")
public interface IUserRoleService extends IBaseService<UserRole> {
}

