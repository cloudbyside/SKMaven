package com.ylz.redis;

import com.ylz.entity.Seckill;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by liuburu on 2017/2/27.
 */
@Repository
public interface RedisDao {

    boolean saveObject(Object obj);

    Seckill getSeckill(int id);

    Jedis getJedis();

}




