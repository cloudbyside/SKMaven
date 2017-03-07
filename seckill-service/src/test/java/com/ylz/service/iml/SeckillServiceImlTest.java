package com.ylz.service.iml;

import com.alibaba.fastjson.JSON;
import com.ylz.base.SeckillResultData;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.dto.UserSucessKillsDTO;
import com.ylz.entity.Seckill;
import com.ylz.exception.NoSuchSeckillException;
import com.ylz.exception.RepeatSeckillException;
import com.ylz.exception.StoreEmptyException;
import com.ylz.redis.RedisDao;
import com.ylz.redis.proxy.RedisSeckillProxy;
import com.ylz.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by liuburu on 2017/2/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-redis.xml",
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceImlTest {

    @Autowired(required = false)
    private SeckillService seckillService;

    @Test
    public void excuteSeckill()  {
        try {
            ExposerResult exposerResult = seckillService.acquireSeckillURL(19);
            String md5 = exposerResult.getMd5();
            ExecuteSeckillResult executeSeckillResult = seckillService.excuteSeckill(2, 15270998540L, md5);
            System.out.println(JSON.toJSONString(executeSeckillResult));
        } catch (NoSuchSeckillException e) {
            e.printStackTrace();
        } catch (StoreEmptyException e) {
            e.printStackTrace();
        } catch (RepeatSeckillException e) {
           // e.printStackTrace();
            System.out.println("重复秒杀啊；快捷键；拉萨附近");
        }
    }
    @Test
    public void selectOneSeckill() throws Exception {
        Seckill seckill = seckillService.selectOneSeckill(2);
        System.out.println(JSON.toJSONString(seckill));
    }

    @Test
    public void selectSeckillByPage() throws Exception {
        List<Seckill> seckills = seckillService.selectSeckillByPage(1,5,1,"desc");
        for (Seckill sk:seckills){
            System.out.println(JSON.toJSONString(sk));
        }
    }

    @Test
    public void acquireSeckillURL() {
        ExposerResult exposerResult = seckillService.acquireSeckillURL(19);
        System.out.println(JSON.toJSONString(exposerResult));
    }

    @Test
    public void testJunit(){
        ExecuteSeckillResult executeSeckillResult = seckillService.excuteSeckill(19, 15270998540L, "605ce498c0f1c865df1425d8f05d2667");
        SeckillResultData<ExecuteSeckillResult> seckillResultData = new SeckillResultData<ExecuteSeckillResult>(true, executeSeckillResult);
        System.out.println(seckillResultData);
    }

    @Test
    public void fun(){
        System.out.println(seckillService.selectAllCount());
    }


    @Test
    public void testJunit1(){
        try {
            ExecuteSeckillResult executeSeckillResult = seckillService.excuteSeckillByPro(19, 15270998540L, "605ce498c0f1c865df1425d8f05d2667");
            SeckillResultData<ExecuteSeckillResult> seckillResultData = new SeckillResultData<ExecuteSeckillResult>(true, executeSeckillResult);
            System.out.println(JSON.toJSONString(seckillResultData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Autowired(required = false)
    private RedisDao redisDao;

    @Test
    public void test4() {
        RedisSeckillProxy daoProxy1 = new RedisSeckillProxy(redisDao);
//        Seckill seckill = new Seckill();
//        seckill.setSeckillId(101);
//        seckill.setName("卡卡罗特");
//        System.out.println(daoProxy1.saveObject(seckill));
        System.out.println(JSON.toJSONString(daoProxy1.getSeckill(101)));
        System.out.println(JSON.toJSONString(daoProxy1.getSeckill(2)));
        System.out.println(JSON.toJSONString(daoProxy1.getSeckill(19)));
    }


    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test5(){
        System.out.println(jedisPool);
    }

    @Test
    public void test6(){
        List<UserSucessKillsDTO> userSucessKillsDTOS = seckillService.queryUserSeckResult(15270998540L, 1, 5);
        System.out.println(JSON.toJSONString(userSucessKillsDTOS));
    }

}