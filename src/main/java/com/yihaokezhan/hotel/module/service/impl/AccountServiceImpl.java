package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.mapper.AccountMapper;
import com.yihaokezhan.hotel.module.service.IAccountService;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录账号表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account>
        implements IAccountService {


    @Autowired
    private IAccountRoleService accountRoleService;


    @Override
    public Account join(Account account) {
        if (account == null) {
            return account;
        }
        account.setRoles(accountRoleService.mList(M.m().put("accountUuid", account.getUuid())));
        return account;
    }

    @Override
    public QueryWrapper<Account> getWrapper(Map<String, Object> params) {
        QueryWrapper<Account> wrapper = new QueryWrapper<Account>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "userUuid");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "account");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
