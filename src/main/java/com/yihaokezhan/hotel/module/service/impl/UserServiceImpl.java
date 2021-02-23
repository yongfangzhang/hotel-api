package com.yihaokezhan.hotel.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.mapper.UserMapper;
import com.yihaokezhan.hotel.module.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getByOpenId(String openId) {
        return this.getOne(getWrapper(M.m().put("openId", openId)));
    }

    @Override
    public User getByMap(M params) {
        return this.getOne(getWrapper(params));
    }

    private QueryWrapper<User> getWrapper(M params) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        // @formatter:off
        String[] eqFields = new String[] {
            "uuid", "tenantUuid", "gender", "mobile", "unionId", "openId"
        };
        String[] likeFields = new String[] {"name", "nickname"};
        // @formatter:on

        for (String field : eqFields) {
            wrapper.eq(params.containsKey(field), StringUtils.camelToUnderline(field),
                    params.get(field));
        }

        for (String field : likeFields) {
            wrapper.like(params.containsKey(field), StringUtils.camelToUnderline(field),
                    params.get(field));
        }

        return wrapper;
    }
}
