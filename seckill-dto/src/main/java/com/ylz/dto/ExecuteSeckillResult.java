package com.ylz.dto;

import com.ylz.entity.SuccessKilled;
import com.ylz.enums.SeckillEnums;

/**
 * 秒杀执行结果报文数据
 * Created by liuburu on 2017/2/18.
 */
public class ExecuteSeckillResult {
    private boolean success;        //秒杀执行状态
    private SuccessKilled successKilled;    //秒杀成功产品
    private SeckillEnums seckillEnum;       //秒杀失败状态信息

    /**
     * 秒杀执行成功构造器
     * @param success
     * @param successKilled
     */
    public ExecuteSeckillResult(boolean success, SuccessKilled successKilled) {
        this.success = success;
        this.successKilled = successKilled;
    }

    /**
     * 秒杀执行失败构造器
     * @param success
     * @param seckillEnum
     */
    public ExecuteSeckillResult(boolean success, SeckillEnums seckillEnum) {
        this.success = success;
        this.seckillEnum = seckillEnum;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    public SeckillEnums getSeckillEnum() {
        return seckillEnum;
    }

    public void setSeckillEnum(SeckillEnums seckillEnum) {
        this.seckillEnum = seckillEnum;
    }
}

