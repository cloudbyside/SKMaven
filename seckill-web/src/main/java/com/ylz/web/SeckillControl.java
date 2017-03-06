package com.ylz.web;

import com.alibaba.fastjson.JSON;
import com.ylz.base.SeckillException;
import com.ylz.base.SeckillPageData;
import com.ylz.base.SeckillResultData;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.entity.Seckill;
import com.ylz.enums.SeckillEnums;
import com.ylz.exception.NoSuchSeckillException;
import com.ylz.exception.RepeatSeckillException;
import com.ylz.exception.SeckillEndException;
import com.ylz.exception.StoreEmptyException;
import com.ylz.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuburu on 2017/2/18.
 */
@Controller
@RequestMapping("seckill")
public class SeckillControl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());//日志打印

    @Autowired
    private SeckillService seckillService; //spring容器注入逻辑层


    /**
     * 使用modal和view进行视图展示和页跳转
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listSeckillPage(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order
    ) {
        logger.info("Begin==>ModelAndView查询数据开始:pageNo={},pageSize={}", pageNo, pageSize);
        List<Seckill> seckills = seckillService.selectSeckillByPage(pageNo, pageSize, sort, order);
        int totalNum = seckillService.selectTotalCount(sort);
        SeckillPageData seckillPageData = new SeckillPageData(pageNo, pageSize, totalNum, seckills);
        Map<String, SeckillPageData> map = new HashMap<String, SeckillPageData>();
        map.put("seckillPageData", seckillPageData);
        logger.info("End==>ModelAndView查询数据结束:pageData={}", seckillPageData);
        return new ModelAndView("list", map);
    }


    /**
     * 使用返回json，提供数据接口
     *
     * @param pageNo
     * @param pageSize
     * @param sort     0：按照创建时间 1：按距离活动开始  2：按距离活动快要结束
     * @param order    asc:升序  desc:降序
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public SeckillPageData<List<Seckill>> querySeckillByPage(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order

    ) {
        logger.info("Begin==>分页查询数据开始:pageNo={},pageSize={}", pageNo, pageSize);
        List<Seckill> seckills = seckillService.selectSeckillByPage(pageNo, pageSize, sort, order);
        int totalNum = seckillService.selectTotalCount(sort);
        SeckillPageData<List<Seckill>> seckillPageData = new SeckillPageData<List<Seckill>>(pageNo, pageSize, totalNum, seckills);
        logger.info("End==>分页查询数据结束:pageData={}", seckillPageData);
        return seckillPageData;
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView queryDetailSeckillMsg(
            @PathVariable(value = "id") Integer id
    ) {
        logger.info("Begin==>单条记录查询ID:{}", id);
        Seckill seckill = seckillService.selectOneSeckill(id);
        SeckillResultData<Seckill> resultData = new SeckillResultData<Seckill>(true, seckill);
        Map<String, SeckillResultData<Seckill>> map = new HashMap<String, SeckillResultData<Seckill>>();
        map.put("resultData", resultData);
        logger.info("End==>单条记录查询ResultData:{}", resultData);
        return new ModelAndView("seckill_list_detail", map);
    }


    @RequestMapping(value = "/{id}/exposer", method = RequestMethod.GET)
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
        } catch (SeckillEndException e) {//秒杀结束异常
            finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_AREADY_CLOSE);
        } catch (SeckillException e) {//内部错误
            finalResult = new SeckillResultData<ExposerResult>(false, SeckillEnums.SECKILL_INNER_ERROR);
        }
        logger.info("End==>商品秒杀地址暴露结束:{}", JSON.toJSON(finalResult));
        return finalResult;
    }


    @RequestMapping(value = "/{id}/{md5}/execution", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResultData<ExecuteSeckillResult> excuteSeckAction(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "md5") String md5,
            @CookieValue(value = "userPhone", required = false) Long userPhone
    ) {
        SeckillResultData<ExecuteSeckillResult> seckillResultData = null;
        if (userPhone == null) {//用户未登录
            seckillResultData = new SeckillResultData<ExecuteSeckillResult>(false, SeckillEnums.SECKILL_USER_NO_LOGIN);
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

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResultData<Date> getNowTime() {
        SeckillResultData<Date> resultData = new SeckillResultData<Date>(true, new Date());
        return resultData;
    }

    @RequestMapping(value = "/query/totals", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResultData<Map<String, Integer>> getAllQueryTypeTotals() {
        SeckillResultData<Map<String, Integer>> resultData = new SeckillResultData<Map<String, Integer>>(true, seckillService.selectAllCount());
        return resultData;
    }

}
