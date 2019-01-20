package com.kevin;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @类名: SentinelTest<br />
 * @包名：com.kevin<br/>
 * @作者：kevin<br/>
 * @时间：2019/1/20 13:35<br/>
 * @版本：1.0<br/>
 * @描述：<br/>
 */
public class SentinelTest {

    @Test
    public void testSentinel() {
        Set<String> sentinel = new HashSet();
        sentinel.add("192.168.85.131:26380");
        sentinel.add("192.168.85.131:26381");
        sentinel.add("192.168.85.131:26382");
        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinel);
        Jedis master = pool.getResource();
        master.set("age", "26");
        System.out.println(master.get("age"));
    }
}
