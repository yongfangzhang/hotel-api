package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.validator.ValidatorUtils;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.model.Query;
import com.yihaokezhan.hotel.module.service.IBaseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/**
 * <p>
 * 基础服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class BaseServiceImpl<C extends BaseMapper<T>, T extends BaseEntity>
        extends ServiceImpl<C, T> implements IBaseService<T> {

    @Override
    @Cacheable(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME,
            key = "'query::list::' + #a0")
    public List<T> mList(Map<String, Object> params) {
        return join(list(getWrapper(params)));
    }

    @Override
    @Cacheable(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME,
            key = "'query::page::' + #a0")
    public Pager<T> mPage(Map<String, Object> params) {
        Page<T> p = new Query<T>(params).getPage();
        page(p, getWrapper(params));
        join(p.getRecords());
        return new Pager<>(p);
    }

    @Override
    @Cacheable(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME,
            key = "'query::one::' + #a0")
    public T mOne(Map<String, Object> params) {
        return join(getOne(getWrapper(params)));
    }

    @Override
    @Cacheable(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "#uuid")
    public T mGet(String uuid) {
        return join(getById(uuid));
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "query", allEntries = true)
    })
    // @formatter:on
    public boolean mCreate(T entity) {
        ValidatorUtils.validateEntity(entity, AddGroup.class);
        return save(entity) && clearRelationCaches(entity);
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "query", allEntries = true),
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "#entity.getUuid()", allEntries = true)
    })
    // @formatter:on
    public boolean mUpdate(T entity) {
        ValidatorUtils.validateEntity(entity, UpdateGroup.class);
        return updateById(entity) && clearRelationCaches(entity);
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "query", allEntries = true),
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "#uuid", allEntries = true)
    })
    // @formatter:on
    public boolean mDelete(String uuid) {
        return removeById(uuid) && clearRelationCaches(uuid);
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        T entity = mGet(uuid);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }
}
