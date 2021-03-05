package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.OrderItem;
import com.yihaokezhan.hotel.module.mapper.OrderMapper;
import com.yihaokezhan.hotel.module.service.IOrderItemService;
import com.yihaokezhan.hotel.module.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheNames = Order.TABLE_NAME)
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IOrderItemService orderItemService;

    @Override
    public Order join(Order order) {
        if (order == null) {
            return order;
        }
        orderItemService.attachOneItems(order, M.m().put("orderUuid", order.getUuid()),
                (record, items) -> {
                    record.setItems(items);
                });
        return order;
    }

    @Override
    public List<Order> join(List<Order> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return orders;
        }

        orderItemService.attachListItems(orders,
                M.m().put("orderUuids",
                        orders.stream().map(Order::getUuid).collect(Collectors.toList())),
                OrderItem::getOrderUuid, (record, itemsMap) -> {
                    record.setItems(itemsMap.get(record.getUuid()));
                });

        return orders;
    }

    @Override
    public QueryWrapper<Order> getWrapper(Map<String, Object> params) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "apartmentUuid");
        WrapperUtils.fillEq(wrapper, params, "accountUuid");
        WrapperUtils.fillEq(wrapper, params, "from");
        WrapperUtils.fillEq(wrapper, params, "number");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "type");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");
        WrapperUtils.fillInList(wrapper, params, "accountUuids", "account_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
