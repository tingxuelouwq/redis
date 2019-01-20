package com.kevin;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @类名: MSTest
 * @包名：com.kevin
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2019/1/20 12:27
 * @版本：1.0
 * @描述：
 */
public class MRTest {

    @Test
    public void testMasterReplica() throws InterruptedException {
        Jedis master = new Jedis("192.168.85.131", 6380);
        Jedis replica1 = new Jedis("192.168.85.131", 6381);
        Jedis replica2 = new Jedis("192.168.85.131", 6382);

        replica1.slaveof("192.168.85.131", 6380);
        replica2.slaveof("192.168.85.131", 6380);

        master.set("name", "kevin");

        String result = replica2.get("name");
        System.out.println(result); // null

        Thread.sleep(3000);
        result = replica2.get("name");
        System.out.println(result); // kevin
    }
}
