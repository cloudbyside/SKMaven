package com.ylz.enums;

/**
 * Created by liuburu on 2017/3/6.
 */
public enum AttributeEnums {
    USER_PHONE("userPhone"),
    SECKILL_ID("seckillId"),
    CREATE_TIME("createTime"),
    RESULT("result")
            ;
    private String skAttr;

    AttributeEnums(String skAttr) {
        this.skAttr = skAttr;
    }

    public String getSkAttr() {
        return skAttr;
    }
}
