package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Apartment;
import com.yihaokezhan.hotel.module.entity.Order;

/**
 * <p>
 * 公寓表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface IApartmentService extends IBaseService<Apartment> {

    void onOrderCreated(Order order);
}
