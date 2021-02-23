package com.yihaokezhan.hotel.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}
