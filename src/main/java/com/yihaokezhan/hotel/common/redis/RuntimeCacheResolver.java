package com.yihaokezhan.hotel.common.redis;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

public class RuntimeCacheResolver extends SimpleCacheResolver {

    protected RuntimeCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        return Arrays.asList(context.getTarget().getClass().getSimpleName());
    }
}
