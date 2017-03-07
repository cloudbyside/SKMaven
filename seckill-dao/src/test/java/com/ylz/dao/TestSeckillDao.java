package com.ylz.dao;

import com.alibaba.fastjson.JSON;
import com.ylz.dto.UserSucessKillsDTO;
import com.ylz.entity.Seckill;
import com.ylz.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuburu on 2017/2/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml"
})
public class TestSeckillDao {

    @Autowired(required = false)
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    @Autowired(required = false)
    private DataSource dataSource;

    @Test
    public void testQuerySeckill() throws SQLException {
        System.out.println(dataSource.getConnection());
        System.out.println(JSON.toJSONString(seckillMapper.selectByPrimaryKey(2)));
    }

    @Test
    public void testSeclectByPage() throws SQLException {
        List<Seckill> seckills = seckillMapper.selectByPage(0,5,3,"desc");
        System.out.println(JSON.toJSONString(seckills));
    }

    @Test
    public void testUpdateSeckill() throws SQLException {
       int rowCount = seckillMapper.updateSeckillNumber(18);
        System.out.println("更新结果:"+rowCount);
    }

    @Test
    public void testInsertDetail() throws SQLException {
        SuccessKilled record = new SuccessKilled();
        record.setSeckillId(2);
        record.setUserPhone(15270998540l);
        int rowCount = successKilledMapper.insertSelective(record);
        System.out.println("影响结果==>"+rowCount);
    }

    @Test
    public void test(){
        Map map = new HashMap();
        map.put("sort",0);
        System.out.println(seckillMapper.selectTotalCount(2));
    }

    @Test
    public void test1(){
        System.out.println(seckillMapper.updateSeckillNumber(2));
    }


    @Autowired
    private ProcedureMapper procedureMapper;

    @Test
    public void testPro(){
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("userPhone",15270998540l);
        map.put("seckillId",2);
        map.put("createTime",new Date());
        map.put("result",0);
        procedureMapper.excuteSeckillPro(map);
        System.out.println(map.get("result"));
    }


    @Test
    public void testQuery1(){
        List<UserSucessKillsDTO> successKilleds = successKilledMapper.queryUserResultsByPage(15270998540l,0,5);
        System.out.println(JSON.toJSONString(successKilleds));

    }

    @Test
    public void testQuery2(){


    }

}
