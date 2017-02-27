package com.ylz.service.iml;

import com.alibaba.fastjson.JSON;
import com.ylz.base.SeckillResultData;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.entity.Seckill;
import com.ylz.exception.NoSuchSeckillException;
import com.ylz.exception.RepeatSeckillException;
import com.ylz.exception.SeckillNoStartException;
import com.ylz.exception.StoreEmptyException;
import com.ylz.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by liuburu on 2017/2/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
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
            ExecuteSeckillResult executeSeckillResult = seckillService.excuteSeckill(19, 15270998540L, md5);
            System.out.println(JSON.toJSONString(executeSeckillResult));
        } catch (NoSuchSeckillException e) {
            e.printStackTrace();
        } catch (StoreEmptyException e) {
            e.printStackTrace();
        } catch (SeckillNoStartException e) {
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
        List<Seckill> seckills = seckillService.selectSeckillByPage(2,5);
        for (Seckill sk:seckills){
            System.out.println(JSON.toJSONString(sk));
        }
    }

    @Test
    public void acquireSeckillURL() {
        ExposerResult exposerResult = seckillService.acquireSeckillURL(2);
        System.out.println(JSON.toJSONString(exposerResult));
    }

    @Test
    public void testJunit(){
        ExecuteSeckillResult executeSeckillResult = seckillService.excuteSeckill(19, 15270998540L, "605ce498c0f1c865df1425d8f05d2667");
        SeckillResultData<ExecuteSeckillResult> seckillResultData = new SeckillResultData<ExecuteSeckillResult>(true, executeSeckillResult);
        System.out.println(seckillResultData);
    }

    public void fun(){
        int a=1;
        String b = "dfsa";
        System.out.println(a);
        System.out.println(b);
        String c = b.concat("_end");
        System.out.println(c);
    }


}