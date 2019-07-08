package com.enorth.dns.dnshosts.vo;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/8
 * */

import java.util.List;

public class Page<T> {
    private int pageNo = 0;
    private int pageSize = 5;
    private int totalRecord;
    private int totalPage;
    private Integer[] pageNos;
    private List<T> results;


    public Page() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Integer[] getPageNos() {
        return pageNos;
    }

    public void setPageNos(Integer[] pageNos) {
        this.pageNos = pageNos;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
