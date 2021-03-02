package com.yihaokezhan.hotel.common.redis;

import java.time.Duration;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CachingConfiguration {
    // public class CachingConfiguration extends CachingConfigurerSupport {


    public final static String CACHE_RESOLVER_NAME = "simpleCacheResolver";

    public final static String NO_CACHE = "noOpCacheManager";

    @Bean
    @Primary
    public CacheManager cacheManager(RedisTemplate<String, Object> cacheRedis) {
        RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(cacheRedis.getConnectionFactory());

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 默认不过期
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ZERO)
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericFastJsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();
        // 初始化RedisCacheManager
        return new MyRedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    @Bean(NO_CACHE)
    public CacheManager noOpCacheManager() {
        return new NoOpCacheManager();
    }

    @Bean(CACHE_RESOLVER_NAME)
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        return new RuntimeCacheResolver(cacheManager);
    }

    public static String getCacheName(String name) {
        String tenant = DynamicTenantHandler.getTenant();
        if (StringUtils.isBlank(tenant)) {
            return name;
        }
        return (tenant + "::" + name);
    }
}
