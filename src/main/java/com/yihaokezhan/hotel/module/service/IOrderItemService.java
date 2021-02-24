package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.OrderItem;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 订单详细表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "OrderItem")
public interface IOrderItemService extends IBaseService<OrderItem> {
}
