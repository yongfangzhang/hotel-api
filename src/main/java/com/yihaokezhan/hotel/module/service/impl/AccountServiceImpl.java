package com.yihaokezhan.hotel.module.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.enums.UserState;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.remark.RemarkUtils;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.RandomUtils;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.common.validator.Assert;
import com.yihaokezhan.hotel.common.validator.ValidatorUtils;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.form.LoginForm;
import com.yihaokezhan.hotel.form.RegisterForm;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.mapper.AccountMapper;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
import com.yihaokezhan.hotel.module.service.IAccountService;
import com.yihaokezhan.hotel.module.service.ITenantService;
import com.yihaokezhan.hotel.module.service.IUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 登录账号表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = Account.TABLE_NAME)
@Slf4j
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private IAccountRoleService accountRoleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true)
    })
    // @formatter:on
    public Account register(RegisterForm form) {
        Account account = form.getAccount();

        account.setSalt(RandomUtils.randomString32());
        account.setPassword(getPasswordHex(account.getPassword(), account.getSalt()));
        account = mCreate(account);

        AccountRole accountRole = new AccountRole();
        accountRole.setAccountUuid(account.getUuid());
        accountRole.setRole(account.getTenantUuid(), account.getType(), form.getRoleType());
        accountRoleService.mCreate(accountRole);

        return mGet(account.getUuid());
    }

    @Override
    public Account login(LoginForm form) {
        try {
            // @formatter:off
            Account account = this.mOne(M.m()
                .put("account", form.getAccount())
                .put("type", form.getType())
            );
            // @formatter:on
            validateAccount(account);
            validatePassword(account, form);
            createToken(account, form);
            return updateAfterLogin(account, form);
        } catch (RRException err) {
            throw err;
        } catch (Exception e) {
            log.error("登录失败", e);
            throw new RRException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true),
        @CacheEvict(key = "#account.getUuid()", allEntries = true)
    })
    // @formatter:on
    public Account mUpdate(Account account) {
        ValidatorUtils.validateEntity(account, UpdateGroup.class);
        RemarkUtils.appendRemark(account, getRemark(account.getUuid()));
        boolean updatePassword = StringUtils.isNotBlank(account.getPassword());
        if (updatePassword) {
            account.setSalt(RandomUtils.randomString32());
            account.setPassword(getPasswordHex(account.getPassword(), account.getSalt()));
        }
        if (updateById(account) && clearRelationCaches()) {
            return account;
        }
        if (updatePassword) {
            Map<String, Integer> accountMap = new HashMap<>();
            accountMap.put(account.getUuid(), account.getType());
            tokenUtils.expireToken(accountMap);
        }
        throw new RRException("更新失败");
    }

    @Override
    public Account join(Account account) {
        if (account == null) {
            return account;
        }
        userService.attachOne(account, account.getUserUuid(), (record, user) -> {
            record.setUser(user);
        });
        accountRoleService.attachOneItems(account, M.m().put("accountUuid", account.getUuid()), (record, roles) -> {
            record.setAccountRoles(roles);
        });
        tenantService.attachOne(account, account.getTenantUuid(), (record, tenant) -> {
            record.setTenant(tenant);
        });
        return account;
    }

    @Override
    public List<Account> join(List<Account> accounts) {
        if (CollectionUtils.isEmpty(accounts)) {
            return accounts;
        }

        userService.attachList(accounts, Account::getUserUuid, (record, userMap) -> {
            record.setUser(userMap.get(record.getUserUuid()));
        });
        accountRoleService.attachListItems(accounts,
                M.m().put("accountUuids", accounts.stream().map(Account::getUuid).collect(Collectors.toList())),
                AccountRole::getAccountUuid, (record, rolesMap) -> {
                    record.setAccountRoles(rolesMap.get(record.getUuid()));
                });
        tenantService.attachList(accounts, Account::getTenantUuid, (record, tenantMap) -> {
            record.setTenant(tenantMap.get(record.getTenantUuid()));
        });
        return accounts;
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

    private void validateAccount(Account account) {
        // @formatter:off
        // 存在性
        Assert.notNull(account, ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
        // 用户存在性
        Assert.notNull(account.getUser(), ErrorCode.USER_NOT_FOUND);
        // 租户存在性
        Assert.notNull(account.getTenant(), ErrorCode.TENANT_NOT_FOUND);
        // 有效性
        Assert.state(!UserState.isForbiddenOrDel(account.getState()), ErrorCode.ACCOUNT_FORBIDDEN);
        // 用户有效性
        Assert.state(!UserState.isForbiddenOrDel(account.getUser().getState()), ErrorCode.USER_FORBIDDEN);
        // 租户有效性
        ValidatorUtils.validateTenant(account.getTenant(), ErrorCode.TENANT_FORBIDDEN);
        // @formatter:on
    }

    private void validatePassword(Account account, LoginForm form) {
        // 密码正确性
        String pwdHash = getPasswordHex(form.getPassword(), account.getSalt());
        log.info("current: {}, saved: {}", pwdHash, account.getPassword());
        Assert.isTrue(account.getPassword().equals(pwdHash), ErrorCode.ACCOUNT_PASSWORD_ERROR);
    }

    private void createToken(Account account, LoginForm form) {
        // 创建Token
        TokenUser tokenUser = new TokenUser(account.getUuid(), account.getType());
        // 自动登录
        if (form.isAutoLogin()) {
            tokenUser.setExpiredAt(LocalDateTime.now().plusMonths(1L));
        }
        tokenUser.setTenantUuid(account.getTenantUuid());
        tokenUser.setAccount(account.getAccount());
        tokenUser.setUserUuid(account.getUserUuid());
        User user = account.getUser();
        if (user != null) {
            tokenUser.setName(user.getName());
        }
        account.setToken(tokenUtils.createToken(tokenUser));
    }

    private Account updateAfterLogin(Account entity, LoginForm form) {
        Account account = new Account();
        account.setUuid(entity.getUuid());
        account.setLastLoginAt(LocalDateTime.now());
        account.setDevice(form.getDevice());
        account.setUserAgent(form.getUserAgent());
        mUpdate(account);
        return entity;
    }

    private String getPasswordHex(String password, String salt) {
        return DigestUtils.md5DigestAsHex(StringUtils.getBytes(password + salt, Constant.CHARSET));
    }
}
