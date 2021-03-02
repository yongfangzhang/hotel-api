package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.mapper.TenantMapper;
import com.yihaokezhan.hotel.module.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = Tenant.TABLE_NAME)
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant>
        implements ITenantService {

    @Autowired
    private CacheRedisService cacheRedisService;

    @Override
    public boolean clearRelationCaches() {
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(Account.TABLE_NAME));
        return true;
    }

    @Override
    public QueryWrapper<Tenant> getWrapper(Map<String, Object> params) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<Tenant>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "contactorMobile");

        WrapperUtils.fillLike(wrapper, params, "name");
        WrapperUtils.fillLike(wrapper, params, "contactor");
        WrapperUtils.fillLike(wrapper, params, "legalPerson");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
