package com.yihaokezhan.hotel.common.redis;

import java.time.Duration;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Configuration
public class RedisConfig {

    @Value("${yhkz.hotel.redis.database.app:0}")
    private int appDatabase;

    @Value("${yhkz.hotel.redis.database.token:1}")
    private int tokenDatabase;

    @Value("${yhkz.hotel.redis.database.cache:2}")
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

    public RedisTemplate<String, Object> getRedisTemplate(int db) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();

        JedisConnectionFactory connFac = jedisConnectionFactory();
        connFac.getStandaloneConfiguration().setDatabase(db);
        template.setConnectionFactory(connFac);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());

        // 开启事务支持
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        return getRedisTemplate(appDatabase);
    }

    @Bean(name = "tokenRedis")
    public RedisTemplate<String, Object> tokenRedisTemplate() {
        return getRedisTemplate(tokenDatabase);
    }

    @Bean(name = "cacheRedis")
    public RedisTemplate<String, Object> cacheRedisTemplate() {
        return getRedisTemplate(cacheDatabase);
    }
}
