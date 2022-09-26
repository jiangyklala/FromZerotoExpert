package com.jiang.fzte.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("124.223.184.187", 6379)) {
            jedis.auth("jiang");
            System.out.println(jedis.get("u:97367228315336704:uN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
