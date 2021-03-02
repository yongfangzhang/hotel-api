package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.RoleRoute;
import com.yihaokezhan.hotel.module.mapper.RoleRouteMapper;
import com.yihaokezhan.hotel.module.service.IRoleRouteService;
import com.yihaokezhan.hotel.module.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色路由表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, cacheNames = RoleRoute.TABLE_NAME)
public class RoleRouteServiceImpl extends BaseServiceImpl<RoleRouteMapper, RoleRoute>
        implements IRoleRouteService {

    @Autowired
    private IRouteService routeService;

    @Override
    public List<RoleRoute> join(List<RoleRoute> roleRouteList) {
        if (CollectionUtils.isEmpty(roleRouteList)) {
            return roleRouteList;
        }

        routeService.attachList(roleRouteList, RoleRoute::getRouteUuid, (record, map) -> {
            record.setRoute(map.get(record.getRouteUuid()));
        });

        return roleRouteList;
    }

    @Override
    public RoleRoute join(RoleRoute roleRoute) {
        if (roleRoute == null) {
            return roleRoute;
        }

        routeService.attachOne(roleRoute, roleRoute.getRouteUuid(), (record, route) -> {
            record.setRoute(route);
        });

        return roleRoute;
    }

    @Override
    public QueryWrapper<RoleRoute> getWrapper(Map<String, Object> params) {
        QueryWrapper<RoleRoute> wrapper = new QueryWrapper<RoleRoute>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "roleUuid");
        WrapperUtils.fillEq(wrapper, params, "routeUuid");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "roleUuids", "role_uuid");
        WrapperUtils.fillInList(wrapper, params, "routeUuids", "route_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
