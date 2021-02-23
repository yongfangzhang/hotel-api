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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRoleRouteService roleRouteService;

    @Override
    public Role mGet(String uuid) {
        return join(this.getById(uuid));
    }

    @Override
    public List<Role> mList(M params) {
        return join(this.list(getWrapper(params)));
    }


    @Override
    public Role mOne(M params) {
        return join(this.getOne(getWrapper(params)));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        Role entity = this.getById(uuid);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private List<Role> join(List<Role> roleList) {

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

    private Role join(Role role) {

        if (role == null) {
            return role;
        }

        role.setRoutes(roleRouteService.mList(M.m().put("roleUuid", role.getUuid())).stream()
                .map(rr -> rr.getRoute()).collect(Collectors.toList()));

        return role;
    }

    private QueryWrapper<Role> getWrapper(M params) {
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "code");

        WrapperUtils.fillLike(wrapper, params, "description");
        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "codes", "code");

        WrapperUtils.fillSelect(wrapper, params);
        return wrapper;
    }
}
