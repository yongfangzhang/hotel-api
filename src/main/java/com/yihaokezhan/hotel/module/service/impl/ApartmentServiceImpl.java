package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Apartment;
import com.yihaokezhan.hotel.module.mapper.ApartmentMapper;
import com.yihaokezhan.hotel.module.service.IApartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公寓表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class ApartmentServiceImpl extends BaseServiceImpl<ApartmentMapper, Apartment>
        implements IApartmentService {

    @Override
    public QueryWrapper<Apartment> getWrapper(Map<String, Object> params) {
        QueryWrapper<Apartment> wrapper = new QueryWrapper<Apartment>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "areaCode");
        WrapperUtils.fillEq(wrapper, params, "longitude");
        WrapperUtils.fillEq(wrapper, params, "latitude");
        WrapperUtils.fillEq(wrapper, params, "geohash4");
        WrapperUtils.fillEq(wrapper, params, "contactorMobile");
        WrapperUtils.fillEq(wrapper, params, "state");

        WrapperUtils.fillLike(wrapper, params, "name");
        WrapperUtils.fillLike(wrapper, params, "shortName");
        WrapperUtils.fillLike(wrapper, params, "address");
        WrapperUtils.fillLike(wrapper, params, "contactor");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
