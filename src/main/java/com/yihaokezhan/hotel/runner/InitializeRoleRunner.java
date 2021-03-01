package com.yihaokezhan.hotel.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
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
    private ShiroUtils shiroUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
