package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.OrderItem;
import com.yihaokezhan.hotel.module.mapper.OrderItemMapper;
import com.yihaokezhan.hotel.module.service.IOrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详细表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItemMapper, OrderItem>
        implements IOrderItemService {

    @Override
    public QueryWrapper<OrderItem> getWrapper(Map<String, Object> params) {
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<OrderItem>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "orderUuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "apartmentUuid");
        WrapperUtils.fillEq(wrapper, params, "roomUuid");
        WrapperUtils.fillEq(wrapper, params, "mobile");
        WrapperUtils.fillEq(wrapper, params, "state");

        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");
        WrapperUtils.fillInList(wrapper, params, "orderUuids", "order_uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");
        WrapperUtils.fillInList(wrapper, params, "roomUuids", "room_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
