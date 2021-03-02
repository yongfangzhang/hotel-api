package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.mapper.RouteMapper;
import com.yihaokezhan.hotel.module.service.IRouteService;
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
@CacheConfig(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, cacheNames = Route.TABLE_NAME)
public class RouteServiceImpl extends BaseServiceImpl<RouteMapper, Route> implements IRouteService {

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
