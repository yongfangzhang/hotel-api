package com.yihaokezhan.hotel.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class RedisService extends RedisOperations {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
}
