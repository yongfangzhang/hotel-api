package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Order;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 订单表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Order.TABLE_NAME)
public interface IOrderService extends IBaseService<Order> {

}

