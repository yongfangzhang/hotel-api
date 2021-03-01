package com.yihaokezhan.hotel.common.redis;

import java.time.Duration;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class MyRedisCacheManager extends RedisCacheManager {
    public MyRedisCacheManager(RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    @NonNull
    protected RedisCache createRedisCache(String name,
            @Nullable RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.split(name, "#");
        if (array != null && cacheConfig != null && array.length > 0) {
            name = array[0];
            // 解析TTL
            if (array.length > 1) {
                long ttl = Long.parseLong(array[1]);
                // 单位秒
                cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
            }
        }
        return super.createRedisCache(name, cacheConfig);
    }

    /**
     * 从上下文中获取租户ID，重写@Cacheable value 值
     * 
     * @param name
     * @return
     */
    @Override
    public Cache getCache(String name) {
        String tenant = DynamicTenantHandler.getTenant();
        if (StringUtils.isBlank(tenant)) {
            return super.getCache(name);
        }
        return super.getCache(tenant + "::" + name);
    }
}
