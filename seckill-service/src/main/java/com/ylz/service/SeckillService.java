package com.ylz.service;

import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.entity.Seckill;
import com.ylz.exception.NoSuchSeckillException;
import com.ylz.exception.RepeatSeckillException;
import com.ylz.exception.StoreEmptyException;
import com.ylz.exception.SeckillNoStartException;

import java.util.List;

/**
 * 秒杀相关的主要业务逻辑
 * Created by liuburu on 2017/2/18.
 */
public interface SeckillService {

    /**
     * 查询秒杀产品总记录条数
     * @return
     */
    int selectTotalCount();

    /**
     * 查询单个秒杀产品
     * @param seckillId
     * @return
     */
     Seckill selectOneSeckill(int seckillId);


    /**
     * 分页查询秒杀产品数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Seckill> selectSeckillByPage(int pageNo,int pageSize);


    /**
     * 获取秒杀接口地址
     * @param seckillId
     * @return
     */
    ExposerResult acquireSeckillURL(int seckillId)
    throws NoSuchSeckillException,StoreEmptyException,SeckillNoStartException;


    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    ExecuteSeckillResult excuteSeckill(int seckillId,long userPhone,String md5)
            throws RepeatSeckillException,StoreEmptyException;




}
