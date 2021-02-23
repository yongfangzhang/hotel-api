package com.yihaokezhan.hotel.common.utils;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public class WrapperUtils {

    public static final String SQL_SELECT = "sqlSelect";

    public static <T> QueryWrapper<T> fillEqs(QueryWrapper<T> wrapper, M params, String[] fields) {

        for (String field : fields) {
            wrapper.eq(params.containsKey(field), StringUtils.camelToUnderline(field),
                    params.get(field));
        }
        return wrapper;
    }

    public static <T> QueryWrapper<T> fillLikes(QueryWrapper<T> wrapper, M params,
            String[] fields) {

        for (String field : fields) {
            wrapper.like(params.containsKey(field), StringUtils.camelToUnderline(field),
                    params.get(field));
        }
        return wrapper;
    }

    public static <T> QueryWrapper<T> fillSelects(QueryWrapper<T> wrapper, M params) {
        if (!params.containsKey(SQL_SELECT)) {
            return wrapper;
        }
        wrapper.select(params.get(SQL_SELECT).toString());
        return wrapper;
    }

    public static <T> QueryWrapper<T> fillInList(QueryWrapper<T> wrapper, M params, String key,
            String column) {
        if (!params.containsKey(key)) {
            return wrapper;
        }
        List<String> items = JSONUtils.parseArray(params.getString(key), String.class);
        // 包含key，如果list为空时返回不存在
        if (CollectionUtils.isEmpty(items)) {
            return wrapper.apply("1<>1");
        }
        return wrapper.in(column, items);
    }
}
