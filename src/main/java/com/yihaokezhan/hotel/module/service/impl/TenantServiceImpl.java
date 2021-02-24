package com.yihaokezhan.hotel.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.mapper.TenantMapper;
import com.yihaokezhan.hotel.module.service.ITenantService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant>
        implements ITenantService {

    @Override
    public QueryWrapper<Tenant> getWrapper(M params) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<Tenant>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "contactorMobile");

        WrapperUtils.fillLike(wrapper, params, "name");
        WrapperUtils.fillLike(wrapper, params, "contactor");
        WrapperUtils.fillLike(wrapper, params, "legalPerson");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
