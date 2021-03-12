package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.Room;

/**
 * <p>
 * 房间表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface IRoomService extends IBaseService<Room> {

    void onOrderCreated(Order order);
}
