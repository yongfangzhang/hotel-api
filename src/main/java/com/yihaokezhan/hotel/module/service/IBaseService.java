package com.yihaokezhan.hotel.module.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.model.Pager;

public interface IBaseService<T> {

    List<T> mList(M params);

    Pager<T> mPage(M params);

    T mGet(String uuid);

    T mOne(M params);

    boolean mCreate(T entity);

    boolean mUpdate(T entity);

    boolean mDelete(String uuid);

    List<RemarkRecord> getRemark(String uuid);

    default QueryWrapper<T> getWrapper(M params) {
        return new QueryWrapper<T>();
    }

    default T join(T entity) {
        return entity;
    }

    default List<T> join(List<T> entities) {
        return entities;
    }
}
