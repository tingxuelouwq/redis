package com.kevin.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class TestAPI
{
    public static void main( String[] args )
    {
        Jedis jedis = new Jedis("192.168.85.131");
        System.out.println(jedis.ping());

//        jedis.set("k1", "v1");
//        jedis.set("k2", "v2");
//        jedis.set("k3", "v3");

        /** key **/
        Set<String> keys = jedis.keys("*");
        keys.forEach(System.out::println);
        System.out.println("jedis.exists====>" + jedis.exists("k2"));
        System.out.println(jedis.ttl("k1"));
        System.out.println("--------------------");

        /** string **/
//        jedis.append("k1", "myredis");
        System.out.println(jedis.get("k1"));
        jedis.set("k4", "k4_redis");
        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");
        System.out.println(jedis.mget("str1", "str2", "str3"));

        /** list **/
        System.out.println("-------------------");
//        jedis.lpush("mylist", "v1", "v2", "v3", "v4", "v5");
        List<String> list = jedis.lrange("mylist", 0, -1);
        list.forEach(System.out::println);

        /** set **/
        System.out.println("-------------------");
        jedis.sadd("orders", "jd001");
        jedis.sadd("orders", "jd002");
        jedis.sadd("orders", "jd003");
        jedis.sadd("orders", "jd004");
        Set<String> set = jedis.smembers("orders");
        set.forEach(System.out::println);
        jedis.srem("orders", "jd002");
        System.out.println(jedis.smembers("orders").size());

        /** hash **/
        System.out.println("-------------------");
        jedis.hset("hash1", "username", "lisi");
        System.out.println(jedis.hget("hash1", "username"));
        Map<String, String> map = new HashMap<>();
        map.put("telphone", "18810662082");
        map.put("address", "beijing");
        map.put("email", "kevin_wangqi@126.com");
        jedis.hmset("hash2", map);
        List<String> result = jedis.hmget("hash2", "telphone", "email");
        result.forEach(System.out::println);

        /** zset **/
        System.out.println("-------------------");
        jedis.zadd("zset01", 60d, "v1");
        jedis.zadd("zset01", 70d, "v2");
        jedis.zadd("zset01", 80d, "v3");
        jedis.zadd("zset01", 90d, "v4");
        Set<String> set1 = jedis.zrange("zset01", 0, -1);
        set1.forEach(System.out::println);
    }
}
