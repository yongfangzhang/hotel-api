package com.yihaokezhan.hotel.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yihaokezhan.hotel.common.enums.RoleType;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.utils.JSONUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.service.IRoleService;
import com.yihaokezhan.hotel.module.service.IRouteService;

import org.apache.commons.lang3.StringUtils;
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
    private IRoleService roleService;

    @Autowired
    private IRouteService routeService;

    @Autowired
    private CacheRedisService cacheRedisService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        clearCache();
        initRoleData();
        initRouteData();
    }

    private void clearCache() {
        cacheRedisService.flushDatabase();
    }

    private void initRoleData() {
        List<Role> roles = roleService.mList(M.m());
        Map<String, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getCode, v -> v));
        for (RoleType v : RoleType.values()) {
            if (RoleType.UNKNOWN.equals(v) || RoleType.END.equals(v) || roleMap.get(v.getCode()) != null) {
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
            List<Route> presetRoutes = JSONUtils.parseArrayFromFile(routesResource.getFile(), Route.class);
            routeService.mBatchCreate(
                    presetRoutes.stream().filter(r -> StringUtils.isBlank(r.getUuid())).collect(Collectors.toList()));
            routeService.mBatchUpdate(presetRoutes.stream().filter(r -> StringUtils.isNotBlank(r.getUuid()))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("init route data error", e);
        }
    }
}
