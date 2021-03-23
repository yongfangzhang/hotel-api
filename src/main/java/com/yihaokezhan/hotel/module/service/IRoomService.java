package com.yihaokezhan.hotel.module.service;

import java.util.List;

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

    void onOrderCanceled(Order order);

    void clearOrderItems(List<String> orderItemUuids);
}
