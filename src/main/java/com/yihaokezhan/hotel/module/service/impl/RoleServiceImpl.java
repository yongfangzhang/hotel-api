package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.RoleRoute;
import com.yihaokezhan.hotel.module.mapper.RoleMapper;
import com.yihaokezhan.hotel.module.service.IRoleRouteService;
import com.yihaokezhan.hotel.module.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheNames = Role.TABLE_NAME)
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRoleRouteService roleRouteService;

    @Autowired
    private CacheRedisService cacheRedisService;

    @Override
    public boolean clearRelationCaches() {
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(AccountRole.TABLE_NAME));
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(Account.TABLE_NAME));
        return true;
    }

    @Override
    public List<Role> join(List<Role> roleList) {

        if (CollectionUtils.isEmpty(roleList)) {
            return roleList;
        }

        roleRouteService.attachListItems(roleList,
                M.m().put("roleUuids",
                        roleList.stream().map(Role::getUuid).collect(Collectors.toList())),
                RoleRoute::getRoleUuid, (record, map) -> {
                    List<RoleRoute> roleRoutes = map.get(record.getUuid());
                    if (!CollectionUtils.isEmpty(roleRoutes)) {
                        record.setRoleRoutes(roleRoutes);
                    }
                });

        return roleList;
    }

    @Override
    public Role join(Role role) {

        if (role == null) {
            return role;
        }

        roleRouteService.attachOneItems(role, M.m().put("roleUuid", role.getUuid()),
                (record, roleRoutes) -> {
                    record.setRoleRoutes(roleRoutes);
                });

        return role;
    }

    @Override
    public QueryWrapper<Role> getWrapper(Map<String, Object> params) {
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "code");
        WrapperUtils.fillEq(wrapper, params, "accountType");

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
