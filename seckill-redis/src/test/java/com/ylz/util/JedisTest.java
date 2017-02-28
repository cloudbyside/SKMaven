package com.ylz.util;

import com.alibaba.fastjson.JSON;
import com.ylz.entity.Seckill;
import com.ylz.redis.RedisDao;
import com.ylz.redis.proxy.RedisDaoProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by liuburu on 2017/2/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-redis.xml"
})
public class JedisTest {

    @Autowired
    @Qualifier(value = "jedisPool")
    JedisPool jedisPool;


    @Test
    public void test1() {
        //  jedis.close();
        System.out.println("getNumActive=" + jedisPool.getNumActive());
        for (int i = 0; i < 30; i++) {
            Jedis jedis = jedisPool.getResource();
            System.out.println(jedis);
            jedis.set("name" + i, "刘卜铷" + i);
            System.out.println(jedis.get("name" + i));
            System.out.println("getNumActive=" + jedisPool.getNumActive());
            //     jedis.close();
            System.out.println();
        }
        System.out.println("程序结束");
    }

    @Test
    public void test3() {
        Jedis jedis = jedisPool.getResource();

    }


    @Autowired
    private RedisDao redisDao;

    @Test
    public void test4() {
        RedisDaoProxy daoProxy1 = new RedisDaoProxy(redisDao);
        Seckill seckill = new Seckill();
        seckill.setSeckillId(101);
        seckill.setName("卡卡罗特");
        System.out.println(daoProxy1.saveObject(seckill));
        System.out.println(JSON.toJSONString(daoProxy1.getSeckill(101)));
    }


}
