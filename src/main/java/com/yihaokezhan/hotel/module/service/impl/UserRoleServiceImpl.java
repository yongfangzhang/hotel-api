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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements IUserRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public List<UserRole> mList(M params) {
        List<UserRole> userRoleList = this.list(getWrapper(params));
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
    public UserRole getByMap(M params) {
        return this.getOne(getWrapper(params));
    }


    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        UserRole entity = getByMap(M.m().put("uuid", uuid).put(WrapperUtils.SQL_SELECT, "remark"));
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private QueryWrapper<UserRole> getWrapper(M params) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<UserRole>();

        String[] eqFields = new String[] {"uuid"};

        WrapperUtils.fillEqs(wrapper, params, eqFields);
        // WrapperUtils.fillLikes(wrapper, params, likeFields);
        WrapperUtils.fillSelects(wrapper, params);
        WrapperUtils.fillInList(wrapper, params, "userUuids", "user_uuid");

        return wrapper;
    }
}
