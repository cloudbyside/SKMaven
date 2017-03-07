package com.ylz.dto;

import java.util.Date;

/**
 * 用户秒杀数据传输类型
 * Created by liuburu on 2017/3/6.
 */
public class UserSucessKillsDTO {
    private String userPhone;
    private String skName;
    private Date skTime;
    private int status;

    public UserSucessKillsDTO() {
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSkName() {
        return skName;
    }

    public void setSkName(String skName) {
        this.skName = skName;
    }

    public Date getSkTime() {
        return skTime;
    }

    public void setSkTime(Date skTime) {
        this.skTime = skTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
