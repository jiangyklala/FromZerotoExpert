package com.jiang.fzte.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("124.223.184.187", 6379)) {
            jedis.auth("jiang");
            String pong = jedis.ping();
            System.out.println("Redis 连接成功" + pong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
