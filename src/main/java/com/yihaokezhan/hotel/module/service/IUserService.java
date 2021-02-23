package com.yihaokezhan.hotel.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
public interface IUserService extends IService<User> {

    User mGet(String uuid);

    User mOne(M params);

    User getByOpenId(String openId);
}
