package com.yihaokezhan.hotel.module.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.model.BaseEntity;
import com.yihaokezhan.hotel.model.Pager;
import org.apache.commons.lang3.StringUtils;

public interface IBaseService<T extends BaseEntity> {

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

    default <C> void attachList(Collection<C> records, Function<? super C, String> uuidFunc,
            BiConsumer<C, Map<String, T>> consumer) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        // @formatter:off
        List<String> uuids = records
            .stream()
            .map(uuidFunc)
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toList());
        // @formatter:on
        attachList(records, uuids, consumer);
    }

    default <C> void attachList(Collection<C> records, Collection<String> uuids,
            BiConsumer<C, Map<String, T>> consumer) {
        if (CollectionUtils.isEmpty(records) || CollectionUtils.isEmpty(uuids)) {
            return;
        }
        attachList(records, M.m().put("uuids", uuids), consumer);
    }

    default <C> void attachOne(C record, String uuid, BiConsumer<C, T> consumer) {
        if (record == null || StringUtils.isBlank(uuid)) {
            return;
        }
        consumer.accept(record, mGet(uuid));
    }

    default <C> void attachList(Collection<C> records, Map<String, Object> params,
            BiConsumer<C, Map<String, T>> consumer) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        List<T> entities = mList(params);
        Map<String, T> map =
                entities.stream().collect(Collectors.toMap(BaseEntity::getUuid, t -> t));
        records.forEach(t -> consumer.accept(t, map));
    }

    default <C> void attachOne(C record, Map<String, Object> params, BiConsumer<C, T> consumer) {
        if (record == null) {
            return;
        }
        consumer.accept(record, mOne(params));
    }

    default <C> void attachListItems(List<C> records, Map<String, Object> params,
            Function<? super T, String> groupFunc, BiConsumer<C, Map<String, List<T>>> consumer) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }

        List<T> entities = mList(params);
        Map<String, List<T>> map = entities.stream().collect(Collectors.groupingBy(groupFunc));

        records.forEach(t -> consumer.accept(t, map));
    }

    default <C> void attachOneItems(C record, Map<String, Object> params,
            BiConsumer<C, List<T>> consumer) {
        if (record == null) {
            return;
        }
        consumer.accept(record, mList(params));
    }

    default boolean clearRelationCaches(T entity) {
        return true;
    }

    default boolean clearRelationCaches(String uuid) {
        return true;
    }
}
