package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.SystemDict;
import com.yihaokezhan.hotel.module.mapper.SystemDictMapper;
import com.yihaokezhan.hotel.module.service.ISystemDictService;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = SystemDict.TABLE_NAME)
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDictMapper, SystemDict> implements ISystemDictService {

    @Override
    public QueryWrapper<SystemDict> getWrapper(Map<String, Object> params) {
        QueryWrapper<SystemDict> wrapper = new QueryWrapper<SystemDict>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "parent_uuid");
        WrapperUtils.fillEq(wrapper, params, "code");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "sequence");

        WrapperUtils.fillLike(wrapper, params, "name");
        WrapperUtils.fillLike(wrapper, params, "extend_field");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "parentUuids", "parent_uuid");
        WrapperUtils.fillInList(wrapper, params, "codes", "code");
        WrapperUtils.fillInList(wrapper, params, "types", "type");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
