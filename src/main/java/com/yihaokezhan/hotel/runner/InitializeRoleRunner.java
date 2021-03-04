package com.yihaokezhan.hotel.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.annotation.DataSource;
import com.yihaokezhan.hotel.common.enums.RoleType;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.common.utils.JSONUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
import com.yihaokezhan.hotel.module.service.IRoleService;
import com.yihaokezhan.hotel.module.service.IRouteService;
import com.yihaokezhan.hotel.module.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


/**
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Component
@Slf4j
public class InitializeRoleRunner implements ApplicationRunner {

    @Autowired
    private IAccountRoleService accountRoleService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRouteService routeService;

    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initRoleData();
        initRouteData();
        List<Tenant> tenants = tenantService.mList(M.m());
        tenants.forEach(tenant -> {
            initAccountRoleCache(tenant.getUuid());
        });
    }

    private void initRoleData() {
        List<Role> roles = roleService.mList(M.m());
        Map<String, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getCode, v -> v));
        for (RoleType v : RoleType.values()) {
            if (RoleType.UNKNOWN.equals(v) || RoleType.END.equals(v)
                    || roleMap.get(v.getCode()) != null) {
                continue;
            }
            Role role = new Role();
            role.setCode(v.getCode());
            role.setName(v.getName());
            role.setDescription(v.getName());
            role.setRemarkContent("自动创建");
            roleService.mCreate(role);
        }
    }

    private void initRouteData() {
        try {
            ClassPathResource routesResource = new ClassPathResource("routes.json");
            List<Route> presetRoutes =
                    JSONUtils.parseArrayFromFile(routesResource.getFile(), Route.class);
            routeService.mBatchCreateOrUpdate(presetRoutes);
        } catch (Exception e) {
            log.error("init route data error", e);
        }
    }

    @DataSource(tenant = "#tenant")
    private void initAccountRoleCache(String tenant) {
        Map<String, List<AccountRole>> accountRoleMap = accountRoleService.mList(M.m()).stream()
                .collect(Collectors.groupingBy(AccountRole::getAccountUuid));

        accountRoleMap.entrySet().forEach(entry -> {
            String accountUuid = entry.getKey();
            List<AccountRole> accountRoles = entry.getValue();
            List<String> perms = accountRoles.stream()
            // @formatter:off
                .filter(ar -> ar.getRole() != null && CollectionUtils.isNotEmpty(ar.getRole().getRoutes()))
                .flatMap(ar -> ar.getRole().getRoutes().stream())
                .filter(r -> CollectionUtils.isNotEmpty(r.getPermissions()))
                .flatMap(r -> r.getPermissions().stream()).distinct()
                .collect(Collectors.toList());
            // @formatter:on

            shiroUtils.updatePermCache(accountUuid, perms);
            shiroUtils.updateRoleCache(accountUuid, accountRoles.stream()
            // @formatter:off
                .filter(ar -> ar.getRole() != null)
                .map(ur -> ur.getRole().getCode())
                .collect(Collectors.toList()));
            // @formatter:on
        });
    }
}
