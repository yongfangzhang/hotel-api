package com.yihaokezhan.hotel.common.redis;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${yhkz.hotel.redis.database.app:0}")
    private int appDatabase;

    @Value("${yhkz.hotel.redis.database.cache:3}")
    private int cacheDatabase;

    @Value("${yhkz.hotel.redis.host:}")
    private String host;

    @Value("${yhkz.hotel.redis.port:}")
    private int port;

    @Value("${yhkz.hotel.redis.passowrd:}")
    private String password;

    @Value("${yhkz.hotel.redis.timeout:}")
    private int timeout;

    @Value("${yhkz.hotel.redis.jedis.pool.max-idle:0}")
    private int maxIdle;

    @Value("${yhkz.hotel.redis.jedis.pool.min-idle:0}")
    private int minIdle;

    @Bean
    public JedisPoolConfig getPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxTotal(300);
        config.setMaxIdle(20);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        return config;
    }

    @Scope(scopeName = "prototype")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig = getPoolConfig();
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setPassword(RedisPassword.of(password));

        JedisClientConfigurationBuilder clientConfig = JedisClientConfiguration.builder();
        clientConfig.connectTimeout(Duration.ofSeconds(timeout));// 6s connection timeout
        clientConfig.usePooling().poolConfig(poolConfig);
        JedisConnectionFactory jedisConFactory =
                new JedisConnectionFactory(redisConfig, clientConfig.build());
        return jedisConFactory;
    }

    @Bean
    public CacheManager cacheManager() {
        // 初始化一个RedisCacheWriter
        JedisConnectionFactory connFac = jedisConnectionFactory();
        connFac.getStandaloneConfiguration().setDatabase(cacheDatabase);
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connFac);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 默认不过期
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ZERO)
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();
        // 初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    public RedisTemplate<String, Object> getRedisTemplate(int db) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();

        JedisConnectionFactory connFac = jedisConnectionFactory();
        connFac.getStandaloneConfiguration().setDatabase(db);
        template.setConnectionFactory(connFac);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);

        // 开启事务支持
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        return getRedisTemplate(appDatabase);
    }
}
