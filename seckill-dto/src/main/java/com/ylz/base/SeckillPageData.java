package com.ylz.base;

/**
 * 秒杀产品分页数据
 * Created by liuburu on 2017/2/19.
 */
public class SeckillPageData<T> {
    private int curNo;      //当前页码
    private int pageSize;   //页码大小
    private int totalNum;   //总记录
    private T pageData;     //分页数据

    public SeckillPageData(int curNo, int pageSize, int totalNum, T pageData) {
        this.curNo = curNo;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.pageData = pageData;
    }

    public int getCurNo() {
        return curNo;
    }

    public void setCurNo(int curNo) {
        this.curNo = curNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    @Override
    public String toString() {
        return "SeckillPageData{" +
                "curNo=" + curNo +
                ", pageSize=" + pageSize +
                ", totalNum=" + totalNum +
                ", pageData=" + pageData +
                '}';
    }
}
