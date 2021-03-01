package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.OrderItem;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 订单详细表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = OrderItem.TABLE_NAME)
public interface IOrderItemService extends IBaseService<OrderItem> {

}

