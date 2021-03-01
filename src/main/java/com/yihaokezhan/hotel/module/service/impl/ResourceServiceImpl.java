package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Resource;
import com.yihaokezhan.hotel.module.mapper.ResourceMapper;
import com.yihaokezhan.hotel.module.service.IResourceService;
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
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource>
        implements IResourceService {


    @Override
    public QueryWrapper<Resource> getWrapper(Map<String, Object> params) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>();


        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "file_type");
        WrapperUtils.fillLike(wrapper, params, "file_name");
        WrapperUtils.fillEq(wrapper, params, "owner_type");
        WrapperUtils.fillEq(wrapper, params, "owner_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
