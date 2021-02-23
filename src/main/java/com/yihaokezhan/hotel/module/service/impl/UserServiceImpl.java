package com.yihaokezhan.hotel.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.mapper.UserMapper;
import com.yihaokezhan.hotel.module.service.IUserRoleService;
import com.yihaokezhan.hotel.module.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public User mGet(String uuid) {
        return join(this.getById(uuid));
    }

    @Override
    public User getByOpenId(String openId) {
        return join(this.getOne(getWrapper(M.m().put("openId", openId))));
    }

    @Override
    public User mOne(M params) {
        return join(this.getOne(getWrapper(params)));
    }

    private User join(User user) {
        if (user == null) {
            return user;
        }
        user.setUserRole(userRoleService.mList(M.m().put("userUuid", user.getUuid())));
        return user;
    }

    private QueryWrapper<User> getWrapper(M params) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "gender");
        WrapperUtils.fillEq(wrapper, params, "mobile");
        WrapperUtils.fillEq(wrapper, params, "unionId");
        WrapperUtils.fillEq(wrapper, params, "openId");

        WrapperUtils.fillLike(wrapper, params, "name");
        WrapperUtils.fillLike(wrapper, params, "nickname");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");

        WrapperUtils.fillSelect(wrapper, params);
        return wrapper;
    }
}
