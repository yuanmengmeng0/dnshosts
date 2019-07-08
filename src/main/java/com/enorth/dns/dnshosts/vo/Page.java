package com.enorth.dns.dnshosts.vo;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/8
 * */

import java.util.List;

public class Page<T> {
    private int pageNo = 1;//页码，默认第一页
    private int pageSize = 3; //每页显示的记录数  默认5
    private int totalRecord; //总记录数
    private int totalPage;  //总页数
    private Integer[] pageNos; //显示的页码
    private List<T> results;
    private int startData;

    public int getStartData() {
        return startData;
    }

    public void setStartData(int startData) {
        this.startData = startData;
    }

    public Page() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if(pageNo <= 0){
            pageNo =1;
        }
        this.pageNo = pageNo;
        int startData = (this.pageNo - 1) *this.pageSize;
        this.setStartData(startData);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize <= 0){
            pageSize = 15;
        }
        if(pageSize > 1000){
            pageSize = 1000;
        }
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        // 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        int totalPage = totalRecord % pageSize ==0 ? totalRecord/pageSize
                : totalRecord / pageSize +1;
        this.setTotalPage(totalPage);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        if(totalPage < pageNo){
            this.setPageNo(totalPage);
        }
        createPageNos(7);
    }

    private void createPageNos(int showCount) {
        Integer[] pageNos;
        if(this.totalPage <= showCount){
            pageNos = new Integer[this.totalPage];
            for (int i=0;i<this.totalPage;i++){
                pageNos[i] = i+1;
            }
        }else{
            pageNos = new Integer[showCount];
            int mid = showCount /2;
            if(pageNo <= mid){
                for (int i=0;i<showCount;i++){
                    pageNos[i]=i+1;
                }
            }
            if (pageNo >= this.totalPage -mid) {
                for (int i = 0; i < showCount; i++) {
                    pageNos[i] = this.totalPage-showCount + i + 1;
                }
            }
            if (pageNo > mid && pageNo < this.totalPage -mid) {
                for (int i = 0; i < showCount; i++) {
                    pageNos[i] = pageNo - mid + i;
                }
            }
        }
        this.pageNos = pageNos;
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
