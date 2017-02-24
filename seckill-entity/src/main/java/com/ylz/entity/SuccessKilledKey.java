package com.ylz.entity;

public class SuccessKilledKey {
    private Integer seckillId;

    private Long userPhone;

    public SuccessKilledKey() {
    }

    public SuccessKilledKey(Integer seckillId, Long userPhone) {
        this.seckillId = seckillId;
        this.userPhone = userPhone;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }
}