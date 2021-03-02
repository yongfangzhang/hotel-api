package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.RoleRoute;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.mapper.RouteMapper;
import com.yihaokezhan.hotel.module.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 路由表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = Route.TABLE_NAME)
public class RouteServiceImpl extends BaseServiceImpl<RouteMapper, Route> implements IRouteService {

    @Autowired
    private CacheRedisService cacheRedisService;

    @Override
    public boolean clearRelationCaches() {
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(RoleRoute.TABLE_NAME));
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(Role.TABLE_NAME));
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(AccountRole.TABLE_NAME));
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(Account.TABLE_NAME));
        return true;
    }

    @Override
    public QueryWrapper<Route> getWrapper(Map<String, Object> params) {
        QueryWrapper<Route> wrapper = new QueryWrapper<Route>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "type");

        WrapperUtils.fillLike(wrapper, params, "path");
        WrapperUtils.fillLike(wrapper, params, "caption");
        WrapperUtils.fillLike(wrapper, params, "permissions");
        WrapperUtils.fillLike(wrapper, params, "description");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
