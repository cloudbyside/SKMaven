package com.ylz.enums;

/**
 * Created by liuburu on 2017/3/5.
 */
public enum  QueryTypeEnum {

    TYPE_0("createTime"),
    TYPE_1("willBegin"),
    TYPE_2("willEnd")
    ;
    private String queryType;

    QueryTypeEnum(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryType() {
        return queryType;
    }
}
