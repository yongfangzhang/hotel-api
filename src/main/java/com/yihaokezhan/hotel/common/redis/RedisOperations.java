package com.yihaokezhan.hotel.common.redis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.JSONUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class RedisOperations {

    public RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    /** 默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    public void set(String key, Object value, long expire) {
        getRedisTemplate().opsForValue().set(key, toJson(value));
        if (expire != Constant.CACHE_DURATION_FOREVER) {
            getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void permanentSet(String key, Object value) {
        set(key, value, Constant.CACHE_DURATION_FOREVER);
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public void add(String key, Object value, long expire) {
        getRedisTemplate().opsForSet().add(key, value);
        if (expire != Constant.CACHE_DURATION_FOREVER) {
            getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        Object value = getRedisTemplate().opsForValue().get(key);
        if (expire != Constant.CACHE_DURATION_FOREVER) {
            getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value.toString(), clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, Constant.CACHE_DURATION_FOREVER);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        Object value = getRedisTemplate().opsForValue().get(key);
        return value == null ? null : fromJsonList(value.toString(), clazz);
    }

    public String get(String key, long expire) {
        Object value = getRedisTemplate().opsForValue().get(key);
        if (expire != Constant.CACHE_DURATION_FOREVER) {
            getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : value.toString();
    }

    public String get(String key) {
        return get(key, Constant.CACHE_DURATION_FOREVER);
    }

    public void delete(String key) {
        getRedisTemplate().delete(key);
    }

    public void deleteBatch(String prefix) {
        getRedisTemplate().delete(getRedisTemplate().keys(prefix + ":*"));
    }

    public void delete(Set<String> keys) {
        getRedisTemplate().delete(keys);
    }

    public void delete(List<String> keys) {
        getRedisTemplate().delete(keys);
    }

    public void delete(Stream<String> keys) {
        getRedisTemplate()
                .delete(keys.filter(StringUtils::isNotBlank).collect(Collectors.toList()));
    }

    public long increment(String key) {
        return increment(key, Constant.CACHE_DURATION_FOREVER);
    }

    public long increment(String key, long expire) {
        long ret = getRedisTemplate().opsForValue().increment(key, 1);
        if (expire != Constant.CACHE_DURATION_FOREVER) {
            getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
        }
        return ret;
    }

    public Set<Object> members(String key) {
        return getRedisTemplate().opsForSet().members(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float
                || object instanceof Double || object instanceof Boolean
                || object instanceof String) {
            return String.valueOf(object);
        }
        return JSONUtils.stringify(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        if (Integer.class.equals(clazz) || Long.class.equals(clazz) || Float.class.equals(clazz)
                || Double.class.equals(clazz) || Boolean.class.equals(clazz)
                || String.class.equals(clazz)) {
            return clazz.cast(ConvertUtils.convert(json, clazz));
        }
        return JSONUtils.parse(json, clazz);
    }

    private <T> List<T> fromJsonList(String json, Class<T> clazz) {
        return JSONUtils.parseArray(json, clazz);
    }
}
