package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.mapper.AccountRoleMapper;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
import com.yihaokezhan.hotel.module.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, cacheNames = AccountRole.TABLE_NAME)
public class AccountRoleServiceImpl extends BaseServiceImpl<AccountRoleMapper, AccountRole>
        implements IAccountRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public List<AccountRole> join(List<AccountRole> accountRoles) {

        if (CollectionUtils.isEmpty(accountRoles)) {
            return accountRoles;
        }

        roleService.attachList(accountRoles, AccountRole::getRoleUuid, (record, map) -> {
            record.setRole(map.get(record.getRoleUuid()));
        });
        return accountRoles;
    }

    @Override
    public AccountRole join(AccountRole accountRole) {

        if (accountRole == null) {
            return accountRole;
        }
        roleService.attachOne(accountRole, accountRole.getRoleUuid(), (record, role) -> {
            record.setRole(role);
        });
        return accountRole;
    }

    @Override
    public QueryWrapper<AccountRole> getWrapper(Map<String, Object> params) {
        QueryWrapper<AccountRole> wrapper = new QueryWrapper<AccountRole>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "accountUuid");
        WrapperUtils.fillEq(wrapper, params, "roleUuid");


        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "accountUuids", "account_uuid");
        WrapperUtils.fillInList(wrapper, params, "roleUuids", "role_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
