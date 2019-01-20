package com.kevin;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TXTest {

    @Test
    public void testMultiAndExec() {
        Jedis jedis = new Jedis("192.168.85.131");
        Transaction tx = jedis.multi();
        tx.set("k1", "v11");
        tx.set("k2", "v21");
//        tx.exec();
        tx.discard();
    }

    @Test
    public void testWatch() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.85.131");
        int balance;            // 可用余额
        int debt;               // 欠额
        int amtToSubtract = 10; // 实刷额度

        jedis.watch("balance");
        balance = Integer.parseInt(jedis.get("balance"));
        Thread.sleep(5000);     // 模拟生产，有其他人修改了balance
        if (balance < amtToSubtract) {
            jedis.unwatch();
            System.out.println("modified");
        } else {
            System.out.println("************transaction");
            Transaction tx = jedis.multi();
            tx.decrBy("balance", amtToSubtract);
            tx.incrBy("debt", amtToSubtract);
            List<Object> result = tx.exec();
            result.forEach(System.out::println);
            balance = Integer.parseInt(jedis.get("balance"));
            debt = Integer.parseInt(jedis.get("debt"));
            System.out.println("************" + balance);
            System.out.println("************" + debt);
        }
    }
}
