package com.ylz.base;

/**
 * Created by liuburu on 2017/2/18.
 */
public class SeckillResultData<T> {
    private boolean success;
    private T data;
    private String errMsg;

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
     * @param errMsg
     */
    public SeckillResultData(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
