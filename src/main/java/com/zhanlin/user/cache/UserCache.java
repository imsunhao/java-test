package com.zhanlin.user.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class UserCache {
  @Autowired
  JedisPool jedisPool;

  public String set(String username) {
    try (Jedis jedis = jedisPool.getResource()) {
      return jedis.set("test", username);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String get() {
    try (Jedis jedis = jedisPool.getResource()) {
      return jedis.get("test");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
