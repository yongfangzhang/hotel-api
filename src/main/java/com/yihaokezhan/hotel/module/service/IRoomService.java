package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Room;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 房间表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Room.TABLE_NAME)
public interface IRoomService extends IBaseService<Room> {

}

