package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Product;
import com.yihaokezhan.hotel.module.mapper.ProductMapper;
import com.yihaokezhan.hotel.module.service.IProductService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = Product.TABLE_NAME)
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product>
        implements IProductService {


    @Override
    public QueryWrapper<Product> getWrapper(Map<String, Object> params) {
        QueryWrapper<Product> wrapper = new QueryWrapper<Product>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenant_uuid");
        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
