package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Order;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "Order")
public interface IOrderService extends IBaseService<Order> {
}

