package com.ylz.dto;

import com.ylz.entity.Seckill;
import com.ylz.enums.SeckillEnums;

import java.util.Date;

/**
 * Created by liuburu on 2017/2/18.
 */
public class ExposerResult {
    private boolean exposed;    //是否暴露秒杀地址
    private int seckillId;      //秒杀产品ID
    private String md5;         //加密秒杀地址
    private Seckill seckill;     //秒杀产品信息
    private SeckillEnums seckillEnum;   //秒杀错误信息

    private Date beginTime;         //秒杀开始时间
    private Date endTime;           //秒杀结束时间
    private Date nowTime;           //当前秒杀时间


    /**
     * 成功数据构造器
     * @param exposed
     * @param seckillId
     * @param md5
     * @param seckill
     */
    public ExposerResult(boolean exposed, int seckillId, String md5, Seckill seckill) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.md5 = md5;
        this.seckill = seckill;
    }

    /**
     * 失败数据构造器
     * @param exposed
     * @param seckillId
     * @param seckillEnum
     * @param beginTime
     * @param endTime
     * @param nowTime
     */
    public ExposerResult(boolean exposed, int seckillId, SeckillEnums seckillEnum, Date beginTime, Date endTime, Date nowTime) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.seckillEnum = seckillEnum;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.nowTime = nowTime;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public int getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(int seckillId) {
        this.seckillId = seckillId;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public SeckillEnums getSeckillEnum() {
        return seckillEnum;
    }

    public void setSeckillEnum(SeckillEnums seckillEnum) {
        this.seckillEnum = seckillEnum;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
