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
    public List<RoleRoute> mList(M params) {
        List<RoleRoute> roleRouteList = this.list(getWrapper(params));
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

    @Override
    public RoleRoute getByMap(M params) {
        return this.getOne(getWrapper(params));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        RoleRoute entity = getByMap(M.m().put("uuid", uuid).put(WrapperUtils.SQL_SELECT, "remark"));
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private QueryWrapper<RoleRoute> getWrapper(M params) {
        QueryWrapper<RoleRoute> wrapper = new QueryWrapper<RoleRoute>();

        String[] eqFields = new String[] {"uuid"};

        WrapperUtils.fillEqs(wrapper, params, eqFields);
        // WrapperUtils.fillLikes(wrapper, params, likeFields);
        WrapperUtils.fillSelects(wrapper, params);
        WrapperUtils.fillInList(wrapper, params, "roleUuids", "role_uuid");

        return wrapper;
    }
}
