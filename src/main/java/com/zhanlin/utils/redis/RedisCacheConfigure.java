package com.zhanlin.utils.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisCacheConfigure extends CachingConfigurerSupport {
  @Value("${redis.host}")
  String host;
  @Value("${redis.port}")
  Integer port;
  @Value("${redis.pool.max-Total}")
  Integer maxTotal;
  @Value("${redis.pool.max-idle}")
  Integer maxIdle;
  @Value("${redis.pool.min-idle}")
  Integer minIdle;
  @Value("${redis.pool.max-wait}")
  Long maxWaitMillis;

  @Bean
  public JedisPool redisPoolFactory() {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(maxTotal);
    jedisPoolConfig.setMaxIdle(maxIdle);
    jedisPoolConfig.setMinIdle(minIdle);
    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
    return new JedisPool(jedisPoolConfig, host, port);
  }
}
