package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.WxUserInfo;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 微信绑定的用户信息 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = WxUserInfo.TABLE_NAME)
public interface IWxUserInfoService extends IBaseService<WxUserInfo> {

}

