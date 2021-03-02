package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.User;


/**
 * <p>
 * 用户表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface IUserService extends IBaseService<User> {

    User mGetByOpenId(String openId);

}

