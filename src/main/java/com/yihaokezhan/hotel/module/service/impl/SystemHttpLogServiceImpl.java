package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.model.Query;
import com.yihaokezhan.hotel.module.entity.SystemHttpLog;
import com.yihaokezhan.hotel.module.mapper.SystemHttpLogMapper;
import com.yihaokezhan.hotel.module.service.ISystemHttpLogService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网络日志表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = SystemHttpLog.TABLE_NAME)
public class SystemHttpLogServiceImpl extends ServiceImpl<SystemHttpLogMapper, SystemHttpLog>
        implements ISystemHttpLogService {

    @Override
    @Cacheable(key = "'query::page::' + #a0")
    public Pager<SystemHttpLog> mPage(Map<String, Object> params) {
        Page<SystemHttpLog> page = new Query<SystemHttpLog>(params).getPage();
        page(page, getWrapper(params));
        return new Pager<>(page);
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true)
    })
    // @formatter:on
    public boolean mCreate(SystemHttpLog entity) {
        return save(entity);
    }

    private QueryWrapper<SystemHttpLog> getWrapper(Map<String, Object> params) {
        QueryWrapper<SystemHttpLog> wrapper = new QueryWrapper<SystemHttpLog>();

        WrapperUtils.fillEq(wrapper, params, "id");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "module");
        WrapperUtils.fillEq(wrapper, params, "method");
        WrapperUtils.fillEq(wrapper, params, "path");
        WrapperUtils.fillEq(wrapper, params, "status");
        WrapperUtils.fillEq(wrapper, params, "ip");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
