package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.model.Query;
import com.yihaokezhan.hotel.module.entity.SystemLog;
import com.yihaokezhan.hotel.module.mapper.SystemLogMapper;
import com.yihaokezhan.hotel.module.service.ISystemLogService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, cacheNames = SystemLog.TABLE_NAME)
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog>
        implements ISystemLogService {

    @Override
    @Cacheable(key = "'query::page::' + #a0")
    public Pager<SystemLog> mPage(Map<String, Object> params) {
        Page<SystemLog> page = new Query<SystemLog>(params).getPage();
        page(page, getWrapper(params));
        return new Pager<>(page);
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true)
    })
    // @formatter:on
    public boolean mCreate(SystemLog entity) {
        return save(entity);
    }

    private QueryWrapper<SystemLog> getWrapper(Map<String, Object> params) {
        QueryWrapper<SystemLog> wrapper = new QueryWrapper<SystemLog>();

        WrapperUtils.fillEq(wrapper, params, "id");
        WrapperUtils.fillEq(wrapper, params, "tenant_uuid");
        WrapperUtils.fillEq(wrapper, params, "account_uuid");
        WrapperUtils.fillEq(wrapper, params, "account_type");
        WrapperUtils.fillEq(wrapper, params, "target");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "linked");
        WrapperUtils.fillEq(wrapper, params, "ip");
        WrapperUtils.fillEq(wrapper, params, "account_name");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
