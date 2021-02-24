package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.UserRole;
import com.yihaokezhan.hotel.module.mapper.UserRoleMapper;
import com.yihaokezhan.hotel.module.service.IRoleService;
import com.yihaokezhan.hotel.module.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole>
        implements IUserRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public List<UserRole> join(List<UserRole> userRoleList) {

        if (CollectionUtils.isEmpty(userRoleList)) {
            return userRoleList;
        }

        Map<String, Role> roleMap = roleService
                .mList(M.m().put("uuids",
                        userRoleList.stream().map(UserRole::getRoleUuid)
                                .collect(Collectors.toList())))
                .stream().collect(Collectors.toMap(Role::getUuid, v -> v));

        userRoleList.forEach(ur -> {
            ur.setRole(roleMap.get(ur.getRoleUuid()));
        });

        return userRoleList;
    }

    @Override
    public UserRole join(UserRole userRole) {

        if (userRole == null) {
            return userRole;
        }

        userRole.setRole(roleService.mOne(M.m().put("uuid", userRole.getRoleUuid())));

        return userRole;
    }

    @Override
    public QueryWrapper<UserRole> getWrapper(Map<String, Object> params) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<UserRole>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "userUuid");
        WrapperUtils.fillEq(wrapper, params, "roleUuid");


        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "userUuids", "user_uuid");
        WrapperUtils.fillInList(wrapper, params, "roleUuids", "role_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
