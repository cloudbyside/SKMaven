package com.ylz.enums;

/**
 * Created by liuburu on 2017/2/18.
 */
public enum SeckillEnums {


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

    public SeckillEnums stateOf(int stateNum){
        for (SeckillEnums seckillEnum:values()){
            if(seckillEnum.getStateNum()==stateNum){
                return seckillEnum;
            }
        }
        return null;
    }
}
