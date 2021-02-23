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
    public List<Role> mList(M params) {
        List<Role> roleList = this.list(getWrapper(params));
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
    public Role getByMap(M params) {
        return this.getOne(getWrapper(params));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        Role entity = getByMap(M.m().put("uuid", uuid).put(WrapperUtils.SQL_SELECT, "remark"));
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private QueryWrapper<Role> getWrapper(M params) {
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();

        String[] eqFields = new String[] {"uuid"};

        WrapperUtils.fillEqs(wrapper, params, eqFields);
        // WrapperUtils.fillLikes(wrapper, params, likeFields);
        WrapperUtils.fillSelects(wrapper, params);
        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");

        return wrapper;
    }
}
