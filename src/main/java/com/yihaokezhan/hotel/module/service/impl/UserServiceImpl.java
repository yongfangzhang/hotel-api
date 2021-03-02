package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.mapper.UserMapper;
import com.yihaokezhan.hotel.module.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheNames = User.TABLE_NAME)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private CacheRedisService cacheRedisService;

    @Override
    public boolean clearRelationCaches() {
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(Account.TABLE_NAME));
        return true;
    }


    @Override
    public User mGetByOpenId(String openId) {
        return mOne(M.m().put("openId", openId));
    }

    @Override
    public QueryWrapper<User> getWrapper(Map<String, Object> params) {
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

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
