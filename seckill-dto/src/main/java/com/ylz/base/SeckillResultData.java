package com.ylz.base;

import com.ylz.enums.SeckillEnums;

/**
 * Created by liuburu on 2017/2/18.
 */
public class SeckillResultData<T> {
    private boolean success;
    private T data;
    private int stateNum;
    private String stateMsg;

    /**
     * 成功报文构造器
     * @param success
     * @param data
     */
    public SeckillResultData(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 失败报文构造器
     * @param success
     * @param errMsg  枚举对象
     */
    public SeckillResultData(boolean success, SeckillEnums errMsg) {
        this.success = success;
        this.stateNum = errMsg.getStateNum();
        this.stateMsg = errMsg.getStateInfo();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }
}
