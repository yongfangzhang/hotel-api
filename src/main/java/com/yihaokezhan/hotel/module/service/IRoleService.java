package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Role;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "Role")
public interface IRoleService extends IBaseService<Role> {
}
