package com.uiautofree.common.config.global;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
    private List<T> data;
    private Integer firstPage;
    private Integer prePage;
    private Integer nextPage;
    private Integer totalPage;
    private Integer currentPage;
    private Long totalCount;
    private Integer pageSize;

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getFirstPage() {
        return 1;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
