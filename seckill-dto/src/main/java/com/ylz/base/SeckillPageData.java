package com.ylz.base;

/**
 * 秒杀产品分页数据
 * Created by liuburu on 2017/2/19.
 */
public class SeckillPageData<T> {
    private int pageNo;      //当前页码
    private int pageSize;   //页码大小
    private int totalNum;   //总记录
    private T pageData;     //分页数据

    public SeckillPageData(int curNo, int pageSize, int totalNum, T pageData) {
        this.pageNo = curNo;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.pageData = pageData;
    }

    public int getCurNo() {
        return pageNo;
    }

    public void setCurNo(int curNo) {
        this.pageNo = curNo;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public T getPageData() {
        return pageData;
    }

    public void setPageData(T pageData) {
        this.pageData = pageData;
    }
}
