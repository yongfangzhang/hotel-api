package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.OrderProduct;
import com.yihaokezhan.hotel.module.mapper.OrderProductMapper;
import com.yihaokezhan.hotel.module.service.IOrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = OrderProduct.TABLE_NAME)
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProductMapper, OrderProduct>
        implements IOrderProductService {


    @Autowired
    private CacheRedisService cacheRedisService;

    @Override
    public boolean clearRelationCaches() {
        cacheRedisService.deleteBatch(CachingConfiguration.getCacheName(Order.TABLE_NAME));
        return true;
    }

    @Override
    public QueryWrapper<OrderProduct> getWrapper(Map<String, Object> params) {
        QueryWrapper<OrderProduct> wrapper = new QueryWrapper<OrderProduct>();
        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "orderUuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "productUuid");
        WrapperUtils.fillLike(wrapper, params, "productName");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");
        WrapperUtils.fillInList(wrapper, params, "orderUuids", "order_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
