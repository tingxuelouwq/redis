package com.kevin.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @类名: JedisPoolUtil<br />
 * @包名：com.kevin.redis.util<br/>
 * @作者：kevin<br/>
 * @时间：2019/1/20 13:54<br/>
 * @版本：1.0<br/>
 * @描述：<br/>
 */
public class JedisPoolUtil {

    private static volatile JedisSentinelPool jedisPool;

    private JedisPoolUtil() {}

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(300);    // 设置最大连接数
        config.setMaxIdle(50);      // 设置最大空闲连接数
        config.setMinIdle(8);       // 设置最小空闲连接数
        config.setMaxWaitMillis(10000); // 设置获取连接的最大等待时间
        config.setTestOnBorrow(true);   // 获取连接时是否检查有效性
        config.setTestWhileIdle(true);  // 是否检测空闲连接的有效性
        config.setTimeBetweenEvictionRunsMillis(30000);     // 每隔多长时间运行一次空闲连接回收器
        config.setNumTestsPerEvictionRun(10);               // 空闲连接回收器每次运行时检查的连接数量
        config.setMinEvictableIdleTimeMillis(60000);        // 连接空闲多长时间才能被空闲连接回收器扫描并决定是否回收
        config.setSoftMinEvictableIdleTimeMillis(1800000);  // 当一个连接的空闲时间大于该值且空闲连接数大于最小空闲连接数时直接回收

        Set<String> sentinel = new HashSet<>();
        sentinel.add("192.168.85.131:26380");
        sentinel.add("192.168.85.131:26381");
        sentinel.add("192.168.85.131:26382");
        jedisPool = new JedisSentinelPool("mymaster", sentinel, config);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

    public static void closeJedisPool() {
        if (null != jedisPool) {
            jedisPool.destroy();
        }
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();
        jedis.set("happy", "new year");
        System.out.println(jedis.get("happy"));
        closeJedis(jedis);
        closeJedisPool();
    }
}
