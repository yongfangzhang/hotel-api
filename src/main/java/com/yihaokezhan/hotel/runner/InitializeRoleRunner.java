package com.yihaokezhan.hotel.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.UserRole;
import com.yihaokezhan.hotel.module.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeRoleRunner implements ApplicationRunner {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, List<UserRole>> userRoleMap = userRoleService.mList(M.m()).stream()
                .collect(Collectors.groupingBy(UserRole::getUserUuid));

        userRoleMap.entrySet().forEach(entry -> {
            String userUuid = entry.getKey();
            List<UserRole> userRoles = entry.getValue();
            List<String> perms =
                    userRoles.stream().flatMap(ur -> ur.getRole().getRoutes().stream())
                            .flatMap(r -> new ArrayList<String>(
                                    Arrays.asList(r.getPermissions().split(","))).stream())
                            .distinct().collect(Collectors.toList());

            shiroUtils.updatePermCache(userUuid, perms);
            shiroUtils.updateRoleCache(userUuid, userRoles.stream()
                    .map(ur -> ur.getRole().getCode()).collect(Collectors.toList()));
        });

    }
}
