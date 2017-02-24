package com.ylz.entity;

import java.util.Date;

public class SuccessKilled extends SuccessKilledKey {
    private Integer status;

    private Date createTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}