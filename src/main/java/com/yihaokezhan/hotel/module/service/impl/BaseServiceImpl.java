package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.Constant;
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

    @Cacheable(key = Constant.CACHE_PREFIX_LIST + "#a1")
    public List<T> bList(Map<String, Object> params) {
        return list(getWrapper(params));
    }

    @Cacheable(key = Constant.CACHE_PREFIX_PAGE + "#a1")
    public Page<T> bPage(Map<String, Object> params) {
        Page<T> p = new Query<T>(params).getPage();
        return page(p, getWrapper(params));
    }

    @Cacheable(key = Constant.CACHE_PREFIX_ONE + "#a1")
    public T bOne(Map<String, Object> params) {
        return getOne(getWrapper(params));
    }

    @Cacheable(key = "#uuid")
    public T bGet(String uuid) {
        return getById(uuid);
    }

    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = Constant.CACHE_PREFIX_QUERY, allEntries = true)
    })
    // @formatter:on
    public boolean bCreate(T entity) {
        return save(entity);
    }

    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = Constant.CACHE_PREFIX_QUERY, allEntries = true),
        @CacheEvict(key = "#entity.getUuid()", allEntries = true)
    })
    // @formatter:on
    public boolean bUpdate(T entity) {
        return updateById(entity);
    }

    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = Constant.CACHE_PREFIX_QUERY, allEntries = true),
        @CacheEvict(key = "#uuid()", allEntries = true)
    })
    // @formatter:on
    public boolean bRemove(String uuid) {
        return removeById(uuid);
    }

    @Override
    public List<T> mList(Map<String, Object> params) {
        return join(bList(params));
    }

    @Override
    public Pager<T> mPage(Map<String, Object> params) {
        Page<T> p = bPage(params);
        join(p.getRecords());
        return new Pager<>(p);
    }

    @Override
    public T mGet(String uuid) {
        return join(bGet(uuid));
    }

    @Override
    public T mOne(Map<String, Object> params) {
        return join(bOne(params));
    }

    @Override
    public boolean mCreate(T entity) {
        ValidatorUtils.validateEntity(entity, AddGroup.class);
        return bCreate(entity);
    }

    @Override
    public boolean mUpdate(T entity) {
        ValidatorUtils.validateEntity(entity, UpdateGroup.class);
        return bUpdate(entity);
    }

    @Override
    public boolean mDelete(String uuid) {
        return bRemove(uuid);
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        T entity = bGet(uuid);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }
}
