package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.remark.RemarkUtils;
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
    public T mCreate(T entity) {
        beforeAction(entity, AddGroup.class);
        RemarkUtils.appendRemark(entity);
        if (save(entity) && clearRelationCaches()) {
            return entity;
        }
        throw new RRException("保存失败");
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "query", allEntries = true)
    })
    // @formatter:on
    public List<T> mBatchCreate(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return entities;
        }
        beforeAction(entities, AddGroup.class);
        RemarkUtils.appendRemark(entities);
        if (saveBatch(entities) && clearRelationCaches()) {
            return entities;
        }
        throw new RRException("批量保存失败");
    }


    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
    })
    // @formatter:on
    public List<T> mBatchCreateOrUpdate(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return entities;
        }
        beforeAction(entities, AddGroup.class);
        RemarkUtils.appendRemark(entities);
        if (saveOrUpdateBatch(entities) && clearRelationCaches()) {
            return entities;
        }
        throw new RRException("批量保存失败");
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "query", allEntries = true),
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "#entity.getUuid()", allEntries = true)
    })
    // @formatter:on
    public T mUpdate(T entity) {
        beforeAction(entity, UpdateGroup.class);
        RemarkUtils.appendRemark(entity, getRemark(entity.getUuid()));
        if (updateById(entity) && clearRelationCaches()) {
            return entity;
        }
        throw new RRException("更新失败");
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "query", allEntries = true),
        @CacheEvict(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, key = "#uuid", allEntries = true)
    })
    // @formatter:on
    public boolean mDelete(String uuid) {
        if (removeById(uuid) && clearRelationCaches()) {
            return true;
        }
        throw new RRException("删除失败");
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
