package com.ylz.service;

import com.ylz.base.SeckillException;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.dto.UserSucessKillsDTO;
import com.ylz.entity.Seckill;
import com.ylz.entity.SuccessKilled;
import com.ylz.exception.NoSuchSeckillException;
import com.ylz.exception.RepeatSeckillException;
import com.ylz.exception.StoreEmptyException;
import com.ylz.exception.URLRewriteException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 秒杀相关的主要业务逻辑
 * Created by liuburu on 2017/2/18.
 */
public interface SeckillService {

    /**
     * 查询秒杀产品总记录条数
     * @return
     */
    Map<String,Integer> selectAllCount();

    /**
     * 查询秒杀产品总记录条数
     * @return
     */
    int selectTotalCount(@Param("sort") int sort);

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
    List<Seckill> selectSeckillByPage(int pageNo,int pageSize,int sort,String order);


    /**
     * 获取秒杀接口地址
     * @param seckillId
     * @return
     */
    ExposerResult acquireSeckillURL(int seckillId)
    throws NoSuchSeckillException,StoreEmptyException;


    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    ExecuteSeckillResult excuteSeckill(int seckillId, long userPhone, String md5)
            throws RepeatSeckillException,StoreEmptyException;


     List<UserSucessKillsDTO> queryUserSeckResult(Long userPhone,int pageNo,int pageSize);

     int queryUserSeckResultCount(Long usrePhone);


    /**
     * 使用存储过程进行秒杀动作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws RepeatSeckillException
     * @throws StoreEmptyException
     * @throws SeckillException
     * @throws URLRewriteException
     * @throws Exception
     */
    ExecuteSeckillResult excuteSeckillByPro(int seckillId,Long userPhone,String md5) throws
    RepeatSeckillException,StoreEmptyException,SeckillException,URLRewriteException,Exception;




}
