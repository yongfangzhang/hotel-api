package com.yihaokezhan.hotel.common.redis;

import java.util.Arrays;
import java.util.Collection;
import com.yihaokezhan.hotel.common.exception.RRException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

public class RuntimeCacheResolver extends SimpleCacheResolver {

    protected RuntimeCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Class<?> clz = context.getTarget().getClass();
        CacheConfig configAnnotation = clz.getAnnotation(CacheConfig.class);
        if (configAnnotation == null) {
            configAnnotation = clz.getSuperclass().getAnnotation(CacheConfig.class);
        }
        if (configAnnotation == null) {
            throw new RRException("cache resolve faild");
        }
        return Arrays.asList(configAnnotation.cacheNames());
    }
}
