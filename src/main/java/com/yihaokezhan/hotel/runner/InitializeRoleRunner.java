package com.yihaokezhan.hotel.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.yihaokezhan.hotel.common.enums.RoleType;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
import com.yihaokezhan.hotel.module.service.IRoleService;
import com.yihaokezhan.hotel.module.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Component
public class InitializeRoleRunner implements ApplicationRunner {

    @Autowired
    private IAccountRoleService accountRoleService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IRoleService roleService;


    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Tenant> tenants = tenantService.mList(M.m());
        tenants.forEach(tenant -> {
            DynamicTenantHandler.setTenant(tenant.getUuid());
            initRoleData();
            initRoleCache();
        });
        DynamicTenantHandler.clearTenant();
    }

    private void initRoleData() {
        List<Role> roles = roleService.mList(M.m());
        Map<String, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getCode, v -> v));
        for (RoleType v : RoleType.values()) {
            if (roleMap.get(v.getCode()) == null) {
                Role role = new Role();
                role.setCode(v.getCode());
                role.setName(v.getName());
                role.setDescription(v.getName());
                role.setRemarkContent("自动创建");
                roleService.mCreate(role);
            }
        }
    }

    private void initRoleCache() {
        Map<String, List<AccountRole>> accountRoleMap = accountRoleService.mList(M.m()).stream()
                .collect(Collectors.groupingBy(AccountRole::getAccountUuid));

        accountRoleMap.entrySet().forEach(entry -> {
            String accountUuid = entry.getKey();
            List<AccountRole> accountRoles = entry.getValue();
            List<String> perms =
                    accountRoles.stream().flatMap(ur -> ur.getRole().getRoutes().stream())
                            .flatMap(r -> r.getPermissions().stream()).distinct()
                            .collect(Collectors.toList());

            shiroUtils.updatePermCache(accountUuid, perms);
            shiroUtils.updateRoleCache(accountUuid, accountRoles.stream()
                    .map(ur -> ur.getRole().getCode()).collect(Collectors.toList()));
        });
    }
}
