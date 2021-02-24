package com.yihaokezhan.hotel.module.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.model.Pager;

public interface IBaseService<T> {

    List<T> mList(Map<String, Object> params);

    Pager<T> mPage(Map<String, Object> params);

    T mGet(String uuid);

    T mOne(Map<String, Object> params);

    boolean mCreate(T entity);

    boolean mUpdate(T entity);

    boolean mDelete(String uuid);

    List<RemarkRecord> getRemark(String uuid);

    default QueryWrapper<T> getWrapper(Map<String, Object> params) {
        return new QueryWrapper<T>();
    }

    default T join(T entity) {
        return entity;
    }

    default List<T> join(List<T> entities) {
        return entities;
    }
}
