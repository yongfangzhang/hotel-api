package com.yihaokezhan.hotel.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Token所在的Redis DB
 * 
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Service
public class TokenRedisService extends RedisOperations {

    @Autowired
    @Qualifier("tokenRedis")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
}
