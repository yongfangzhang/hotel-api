package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.RoleRoute;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.mapper.RoleRouteMapper;
import com.yihaokezhan.hotel.module.service.IRoleRouteService;
import com.yihaokezhan.hotel.module.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleRouteServiceImpl extends ServiceImpl<RoleRouteMapper, RoleRoute>
        implements IRoleRouteService {

    @Autowired
    private IRouteService routeService;

    @Override
    public RoleRoute mGet(String uuid) {
        return join(this.getById(uuid));
    }

    @Override
    public List<RoleRoute> mList(M params) {
        return join(this.list(getWrapper(params)));
    }

    @Override
    public RoleRoute mOne(M params) {
        return join(this.getOne(getWrapper(params)));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        RoleRoute entity = this.getById(uuid);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private List<RoleRoute> join(List<RoleRoute> roleRouteList) {
        if (CollectionUtils.isEmpty(roleRouteList)) {
            return roleRouteList;
        }

        Map<String, Route> routeMap = routeService
                .mList(M.m().put("uuids",
                        roleRouteList.stream().map(RoleRoute::getRouteUuid)
                                .collect(Collectors.toList())))
                .stream().collect(Collectors.toMap(Route::getUuid, v -> v));

        roleRouteList.forEach(rr -> {
            rr.setRoute(routeMap.get(rr.getRouteUuid()));
        });

        return roleRouteList;
    }

    private RoleRoute join(RoleRoute roleRoute) {
        if (roleRoute == null) {
            return roleRoute;
        }

        roleRoute.setRoute(routeService.mGet(roleRoute.getRouteUuid()));

        return roleRoute;
    }

    private QueryWrapper<RoleRoute> getWrapper(M params) {
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
