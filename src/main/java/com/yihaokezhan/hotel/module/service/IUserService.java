package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.User;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "User")
public interface IUserService extends IBaseService<User> {

    User mGetByOpenId(String openId);
}
