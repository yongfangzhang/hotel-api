package com.yihaokezhan.hotel.model;

import java.util.LinkedHashMap;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yihaokezhan.hotel.common.utils.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Query<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        // 分页参数
        Object o = params.get(Constant.PAGE);
        if (o != null) {
            if (o instanceof Number) {
                currPage = ((Number) o).intValue();
            } else {
                try {
                    currPage = Integer.parseInt((String) o);
                } catch (Exception e) {
                    currPage = 1;
                }
            }
        }

        o = params.get(Constant.LIMIT);
        if (o != null) {
            if (o instanceof Number) {
                limit = ((Number) o).intValue();
            } else {
                try {
                    limit = Integer.parseInt((String) o);
                } catch (Exception e) {
                    limit = 10;
                }
            }
            // 禁用分页
            if (limit == -1) {
                limit = Integer.MAX_VALUE;
            }
        }

        this.put("offset", (currPage - 1) * limit);
        this.put(Constant.PAGE, currPage);
        this.put(Constant.LIMIT, limit);

        this.page = new Page<T>(currPage, limit);
    }
}
