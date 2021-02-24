package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.mapper.OrderMapper;
import com.yihaokezhan.hotel.module.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public QueryWrapper<Order> getWrapper(Map<String, Object> params) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "apartmentUuid");
        WrapperUtils.fillEq(wrapper, params, "userUuid");
        WrapperUtils.fillEq(wrapper, params, "from");
        WrapperUtils.fillEq(wrapper, params, "number");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "type");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");
        WrapperUtils.fillInList(wrapper, params, "userUuids", "user_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
