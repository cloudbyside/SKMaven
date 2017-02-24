package com.ylz.web;

import com.ylz.dao.SeckillMapper;
import com.ylz.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuburu on 2017/2/18.
 */
@Controller
@RequestMapping("seckill")
public class SeckillControl {

    @Autowired
    private SeckillService seckillService; //spring容器注入逻辑层

//    @Autowired
//    private SeckillMapper seckillMapper;


    //测试页面
    @RequestMapping("/hello")
    public String toHelloPage() {
        System.out.println("is Null?"+seckillService);
        return "hello";
    }


    @RequestMapping("/getJson")
    @ResponseBody
    public Object testJSON() {
        Map<String, String> reusltMap = new HashMap<String, String>();
        reusltMap.put("name", "刘卜铷");
        return reusltMap;
    }

//    @RequestMapping("/list")
//    @ResponseBody
//    public SeckillPageData<List<Seckill>> listSeckillPage(
//            @RequestParam(value = "pageNO", required = false, defaultValue = "1") Integer pageNo,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "8") Integer pageSize
//
//    ) {
//        List<Seckill> seckills = seckillService.selectSeckillByPage(pageNo, pageSize);
//        int totalNum = seckillService.selectTotalCount();
//        SeckillPageData seckillPageData = new SeckillPageData(pageNo, pageSize, totalNum, seckills);
//        return seckillPageData;
//    }
//
//    @RequestMapping("/{id}/detail")
//    @ResponseBody
//    public SeckillResultData<Seckill> queryDetailSeckillMsg(
//            @PathVariable(value = "id") Integer id
//    ) {
//        Seckill seckill = seckillService.selectOneSeckill(id);
//        SeckillResultData<Seckill> resultData = new SeckillResultData<Seckill>(true, seckill);
//        return resultData;
//    }


}
