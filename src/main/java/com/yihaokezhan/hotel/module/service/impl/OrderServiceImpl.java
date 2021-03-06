package com.yihaokezhan.hotel.module.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.enums.DepositState;
import com.yihaokezhan.hotel.common.enums.OrderState;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.MapUtils;
import com.yihaokezhan.hotel.common.utils.RandomUtils;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.OrderItem;
import com.yihaokezhan.hotel.module.entity.OrderProduct;
import com.yihaokezhan.hotel.module.mapper.OrderMapper;
import com.yihaokezhan.hotel.module.service.IOrderItemService;
import com.yihaokezhan.hotel.module.service.IOrderProductService;
import com.yihaokezhan.hotel.module.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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

    @Autowired
    private IOrderProductService orderProductService;

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true)
    })
    // @formatter:on
    public Order mCreate(Order order) {
        order.setNumber(RandomUtils.generateNumer());
        syncMainOrder(order);
        super.mCreate(order);
        orderItemService.mBatchCreate(order.getItems().stream().map(item -> {
            item.setApartmentUuid(order.getApartmentUuid());
            item.setOrderUuid(order.getUuid());
            item.setState(order.getState());
            item.setChannel(order.getChannel());
            return item;
        }).collect(Collectors.toList()));
        return order;
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true),
        @CacheEvict(key = "#order.getUuid()", allEntries = true)
    })
    // @formatter:on
    public Order mUpdate(Order order) {
        OrderState orderState = EnumUtils.valueOf(OrderState.class, order.getState());
        switch (orderState) {
            case FINISHED:
                Order originOrder = getById(order.getUuid());
                order.setFinishedAt(LocalDateTime.now());
                if (DepositState.PAID.getValue().equals(originOrder.getDepositState())) {
                    // 订单完成后将已付押金退掉
                    order.setDepositState(DepositState.REFUNDED.getValue());
                }
                break;
            case CANCELD:
            case ABANDON:
                order.setCanceledAt(LocalDateTime.now());
                break;
            default:
                break;
        }

        if (CollectionUtils.isNotEmpty(order.getItems())) {
            order.getItems().forEach(item -> {
                item.setState(order.getState());
            });
            orderItemService.mBatchUpdate(order.getItems());
        }
        syncMainOrder(order);
        return super.mUpdate(order);
    }

    @Override
    public Order join(Order order) {
        if (order == null) {
            return order;
        }

        M params = M.m().put("orderUuid", order.getUuid());
        orderItemService.attachOneItems(order, params, (record, items) -> {
            record.setItems(items);
        });

        orderProductService.attachOneItems(order, params, (record, items) -> {
            record.setProducts(items);
        });
        return order;
    }

    @Override
    public List<Order> join(List<Order> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return orders;
        }

        List<String> orderUuids = orders.stream().map(Order::getUuid).collect(Collectors.toList());

        M params = M.m().put("orderUuids", orderUuids);

        orderItemService.attachListItems(orders, params, OrderItem::getOrderUuid,
                (record, itemsMap) -> {
                    record.setItems(itemsMap.get(record.getUuid()));
                });

        orderProductService.attachListItems(orders, params, OrderProduct::getOrderUuid,
                (record, itemsMap) -> {
                    record.setProducts(itemsMap.get(record.getUuid()));
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
        WrapperUtils.fillEq(wrapper, params, "operatorUuid");
        WrapperUtils.fillEq(wrapper, params, "from");
        WrapperUtils.fillEq(wrapper, params, "number");
        WrapperUtils.fillEq(wrapper, params, "bizNumber");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "channel");

        WrapperUtils.fillEq(wrapper, params, "mainName");
        WrapperUtils.fillEq(wrapper, params, "mainMobile");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");
        WrapperUtils.fillInList(wrapper, params, "accountUuids", "account_uuid");

        // 有效订单
        wrapper.le(MapUtils.getBoolean(params, "vstate"), "state", OrderState.FINISHED.getValue());

        WrapperUtils.fillStates(wrapper, params);
        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillCreatedTimeAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }

    private void syncMainOrder(Order order) {
        if (order == null || CollectionUtils.isEmpty(order.getItems())) {
            return;
        }
        OrderItem mainItem = order.getItems().get(0);
        if (StringUtils.isNotBlank(mainItem.getName())) {
            order.setMainName(mainItem.getName());
        }
        if (StringUtils.isNotBlank(mainItem.getMobile())) {
            order.setMainMobile(mainItem.getMobile());
        }
    }
}
