package com.yihaokezhan.hotel.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonView(V.XS.class)
public class Pager<T> implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 6633534950722667728L;

    // 总记录数
    private long totalCount;
    // 每页记录数
    private long pageSize;
    // 总页数
    private long totalPage;
    // 当前页数
    private long currPage;
    // 列表数据
    private List<T> list;
    // 额外参数
    private M extra;


    public Pager() {

    }

    /**
     * 分页
     * 
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public Pager(List<T> list, long totalCount, long pageSize, long currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (long) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public Pager(Page<T> page) {
        this.initPage(page);
    }

    /**
     * 分页
     */
    public Pager(Map<String, Object> params) {
        Page<T> page = new Query<T>(params).getPage();
        this.initPage(page);
    }

    /**
     * 克隆分页信息
     */
    public <C> Pager(Pager<C> page, List<T> list) {
        this.list = list;
        this.totalCount = page.getTotalCount();
        this.pageSize = page.getPageSize();
        this.currPage = page.getCurrPage();
        this.totalPage = page.getTotalPage();
        this.extra = page.getExtra();
    }

    private void initPage(Page<T> page) {
        this.list = page.getRecords();
        this.totalCount = page.getTotal();
        this.pageSize = page.getSize();
        this.currPage = page.getCurrent();
        this.totalPage = page.getPages();
    }

}
