package com.ylz.redis.proxy;

import com.ylz.entity.Seckill;
import com.ylz.redis.RedisDao;

/**
 * 代理类专门负责:关闭jedis操作对象
 * Created by liuburu on 2017/2/28.
 */
public class RedisDaoProxy{

    private RedisDao redisDao;

    public RedisDaoProxy(RedisDao redisDao) {
        this.redisDao = redisDao;
}


    public boolean saveObject(Object obj) {
        boolean result = redisDao.saveObject(obj);
        redisDao.getJedis().close();
        return result;
    }

    public Seckill getSeckill(int id) {
        Seckill seckill = redisDao.getSeckill(id);
        redisDao.getJedis().close();
        return seckill;
    }
}
