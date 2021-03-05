package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Resource;
import com.yihaokezhan.hotel.module.mapper.ResourceMapper;
import com.yihaokezhan.hotel.module.service.IResourceService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = Resource.TABLE_NAME)
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource>
        implements IResourceService {


    @Override
    public QueryWrapper<Resource> getWrapper(Map<String, Object> params) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>();


        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "fileType");
        WrapperUtils.fillLike(wrapper, params, "fileName");
        WrapperUtils.fillEq(wrapper, params, "ownerType");
        WrapperUtils.fillEq(wrapper, params, "ownerUuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
