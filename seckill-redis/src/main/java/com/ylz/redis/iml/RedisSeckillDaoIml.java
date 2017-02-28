package com.ylz.redis.iml;

import com.ylz.entity.Seckill;
import com.ylz.redis.RedisDao;
import com.ylz.util.DefaultSerializeUtil;
import com.ylz.util.GoogleSerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by liuburu on 2017/2/28.
 */
@Repository
public class RedisSeckillDaoIml implements RedisDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisPool jedisPool;    //jedisPool连接池


    private Jedis jedis;            //只想当前操作中的jedis对象

    public boolean saveObject(Object obj) {
        Seckill seckill = (Seckill)obj;
        byte[] keyBytes = ("seckill:"+seckill.getSeckillId()).getBytes();
        byte[] valueBytes = GoogleSerializeUtil.serialize(seckill,Seckill.class);
        this.jedis = jedisPool.getResource();
        String result = jedis.set(keyBytes, valueBytes);
        logger.info("==>保存对象结果:"+result);
        return "OK".equals(result)?true:false;
    }

    public Seckill getSeckill(int id) {
        byte[] keyBytes = ("seckill:"+id).getBytes();
        this.jedis = jedisPool.getResource();
        Seckill seckill = (Seckill) GoogleSerializeUtil.unserialize(jedis.get(keyBytes),Seckill.class);
        return seckill;
    }

    public Jedis getJedis() {
        return jedis;
    }
}
