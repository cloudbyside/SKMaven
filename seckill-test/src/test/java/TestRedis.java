import com.alibaba.fastjson.JSON;
import com.ylz.redis.RedisDao;
import com.ylz.redis.proxy.RedisSeckillProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

/**
 * Created by liuburu on 2017/3/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-redis.xml"
})
public class TestRedis {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test1(){
        System.out.println(jedisPool);
    }

    @Autowired
    private RedisDao redisDao;

    @Test
    public void test4() {
        RedisSeckillProxy daoProxy1 = new RedisSeckillProxy(redisDao);
//        Seckill seckill = new Seckill();
//        seckill.setSeckillId(101);
//        seckill.setName("卡卡罗特");
//        System.out.println(daoProxy1.saveObject(seckill));
        System.out.println(JSON.toJSONString(daoProxy1.getSeckill(101)));
        System.out.println(JSON.toJSONString(daoProxy1.getSeckill(1010)));
    }
}
