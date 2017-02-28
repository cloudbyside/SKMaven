package com.ylz.web;

import com.alibaba.fastjson.JSON;
import com.ylz.base.SeckillException;
import com.ylz.base.SeckillPageData;
import com.ylz.base.SeckillResultData;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.entity.Seckill;
import com.ylz.enums.SeckillEnums;
import com.ylz.exception.*;
import com.ylz.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by liuburu on 2017/2/18.
 */
@Controller
@RequestMapping("seckill")
public class SeckillControl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());//日志打印

    @Autowired
    private SeckillService seckillService; //spring容器注入逻辑层

    @RequestMapping("/list")
    @ResponseBody
    public SeckillPageData<List<Seckill>> listSeckillPage(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "8") Integer pageSize

    ) {
        logger.info("Begin==>分页查询数据开始:pageNo={},pageSize={}", pageNo, pageSize);
        List<Seckill> seckills = seckillService.selectSeckillByPage(pageNo, pageSize);
        int totalNum = seckillService.selectTotalCount();
        SeckillPageData seckillPageData = new SeckillPageData(pageNo, pageSize, totalNum, seckills);
        logger.info("End==>分页查询数据结束:pageData={}", seckillPageData);
        return seckillPageData;
    }

    @RequestMapping("/{id}/detail")
    @ResponseBody
    public SeckillResultData<Seckill> queryDetailSeckillMsg(
            @PathVariable(value = "id") Integer id
    ) {
        logger.info("Begin==>单条记录查询ID:{}", id);
        Seckill seckill = seckillService.selectOneSeckill(id);
        SeckillResultData<Seckill> resultData = new SeckillResultData<Seckill>(true, seckill);
        logger.info("End==>单条记录查询ResultData:{}", resultData);
        return resultData;
    }


    @RequestMapping("/{id}/exposer")
    @ResponseBody
    public SeckillResultData<ExposerResult> exposorSeckillURL(
            @PathVariable(value = "id") Integer id
    ) {
        logger.info("Begin==>商品秒杀地址暴露开始ID:{}", id);
        SeckillResultData<ExposerResult> finalResult = null;
        ExposerResult exposerResult = null;
        try {
            exposerResult = seckillService.acquireSeckillURL(id);
            finalResult = new SeckillResultData<ExposerResult>(true, exposerResult);
        } catch (NoSuchSeckillException e) {//没有该商品
             finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_NO_SUCH);
        } catch (StoreEmptyException e) {//库存为空
            finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_REPEAT);
        } catch (SeckillNoStartException e) {//秒杀未开启
            finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_NO_START);
        } catch (SeckillEndException e) {//秒杀结束异常
            finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_AREADY_CLOSE);
        }catch (SeckillException e) {//内部错误
            finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_INNER_ERROR);
        }
        logger.info("End==>商品秒杀地址暴露结束:{}", JSON.toJSON(finalResult));
        return finalResult;
    }


    @RequestMapping("/{id}/{md5}/execution")
    @ResponseBody
    public SeckillResultData<ExecuteSeckillResult> excuteSeckAction(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "md5") String md5,
            @CookieValue(value = "userPhone",required = false) Long userPhone
    ) {
        SeckillResultData<ExecuteSeckillResult> seckillResultData = null;
        if(userPhone==null){//用户未登录
            seckillResultData = new SeckillResultData<ExecuteSeckillResult>(false,SeckillEnums.SECKILL_USER_NO_LOGIN);
        }
        try {
            ExecuteSeckillResult executeSeckillResult = seckillService.excuteSeckill(id, userPhone, md5);
            seckillResultData = new SeckillResultData<ExecuteSeckillResult>(true, executeSeckillResult);
        } catch (RepeatSeckillException e) {//重复秒杀异常
            seckillResultData = new SeckillResultData<ExecuteSeckillResult>(false, SeckillEnums.SECKILL_REPEAT);
        } catch (StoreEmptyException e) {//库存为空异常
            seckillResultData = new SeckillResultData<ExecuteSeckillResult>(false, SeckillEnums.SECKILL_STORE_EMPTY);
        } catch (SeckillException e) {//系统异常
            seckillResultData = new SeckillResultData<ExecuteSeckillResult>(false, SeckillEnums.SECKILL_INNER_ERROR);
        }
        return seckillResultData;
    }

    @RequestMapping("/time/now")
    @ResponseBody
    public SeckillResultData<Date> getNowTime(){
        SeckillResultData<Date> resultData = new SeckillResultData<Date>(true,new Date());
        return resultData;
    }



}
