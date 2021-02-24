package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.RoleRoute;
import com.yihaokezhan.hotel.module.mapper.RoleMapper;
import com.yihaokezhan.hotel.module.service.IRoleRouteService;
import com.yihaokezhan.hotel.module.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRoleRouteService roleRouteService;

    @Override
    public List<Role> join(List<Role> roleList) {

        if (CollectionUtils.isEmpty(roleList)) {
            return roleList;
        }

        Map<String, List<RoleRoute>> roleRoutesMap = roleRouteService
                .mList(M.m().put("roleUuids",
                        roleList.stream().map(Role::getUuid).collect(Collectors.toList())))
                .stream().collect(Collectors.groupingBy(RoleRoute::getRoleUuid));

        roleList.forEach(r -> {
            List<RoleRoute> roleRoutes = roleRoutesMap.get(r.getUuid());
            if (!CollectionUtils.isEmpty(roleRoutes)) {
                r.setRoutes(
                        roleRoutes.stream().map(rr -> rr.getRoute()).collect(Collectors.toList()));
            }
        });

        return roleList;
    }

    @Override
    public Role join(Role role) {

        if (role == null) {
            return role;
        }

        role.setRoutes(roleRouteService.mList(M.m().put("roleUuid", role.getUuid())).stream()
                .map(rr -> rr.getRoute()).collect(Collectors.toList()));

        return role;
    }

    @Override
    public QueryWrapper<Role> getWrapper(M params) {
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "code");

        WrapperUtils.fillLike(wrapper, params, "description");
        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "codes", "code");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
