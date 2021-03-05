package com.yihaokezhan.hotel.common.redis;

import com.yihaokezhan.hotel.common.shiro.ShiroSet;
import com.yihaokezhan.hotel.common.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Cache所在的Redis DB
 * 
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Service
public class CacheRedisService extends RedisOperations {

    @Autowired
    @Qualifier("cacheRedis")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    // TODO: 以下通过代码设置缓存，因为@Cacheable在abstract class/method注解不生效

    public void cacheShiroSet(String uuid, ShiroSet shiroSet) {
        set(Constant.CACHE_PREFIX_SHRIO + uuid, shiroSet);
    }

    public ShiroSet getShiroSet(String uuid) {
        return get(Constant.CACHE_PREFIX_SHRIO + uuid, ShiroSet.class);
    }
}
