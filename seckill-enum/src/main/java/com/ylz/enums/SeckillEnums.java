package com.ylz.enums;

/**
 * Created by liuburu on 2017/2/18.
 */
public enum SeckillEnums {
    SECKILL_INNER_ERROR(1000,"内部异常"),
    SECKILL_REPEAT(1001,"重复秒杀！"),
    SECKILL_NO_SUCH(1002,"没有改秒杀产品异常!"),
    SECKILL_STORE_EMPTY(1003,"  改商品秒杀库存为空！"),
    SECKILL_NO_START(1004,"秒杀未开启"),
    SECKILL_AREADY_CLOSE(1005,"秒杀已经时间已经过了"),
    SECKILL_URL_REWRITE(1006,"秒杀地址被篡改！"),
    SECKILL_USER_NO_LOGIN(1007,"用户未登陆")
    ;


    private int stateNum;
    private String stateInfo;

    SeckillEnums(int stateNum, String stateInfo) {
        this.stateNum = stateNum;
        this.stateInfo = stateInfo;
    }

    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static SeckillEnums stateOf(int stateNum){
        for (SeckillEnums seckillEnum:values()){
            if(seckillEnum.getStateNum()==stateNum){
                return seckillEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SeckillEnums{" +
                "stateNum=" + stateNum +
                ", stateInfo='" + stateInfo + '\'' +
                '}';
    }
}
