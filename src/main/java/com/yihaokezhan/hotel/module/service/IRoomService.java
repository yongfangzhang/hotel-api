package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Room;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 房间表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "Room")
public interface IRoomService extends IBaseService<Room> {
}
