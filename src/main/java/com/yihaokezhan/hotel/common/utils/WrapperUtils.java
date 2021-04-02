package com.yihaokezhan.hotel.common.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public class WrapperUtils {

    public static <T> QueryWrapper<T> fillEq(QueryWrapper<T> wrapper, Map<String, Object> params, String field,
            String column) {
        String v = MapUtils.getString(params, field);
        return wrapper.eq(StringUtils.isNotBlank(v), column, v);
    }

    public static <T> QueryWrapper<T> fillEq(QueryWrapper<T> wrapper, Map<String, Object> params, String field) {
        return fillEq(wrapper, params, field, StringUtils.camelToUnderline(field));
    }

    public static <T> QueryWrapper<T> fillLike(QueryWrapper<T> wrapper, Map<String, Object> params, String field,
            String column) {
        String v = MapUtils.getString(params, field);
        return wrapper.like(StringUtils.isNotBlank(v), column, v);
    }

    public static <T> QueryWrapper<T> fillLike(QueryWrapper<T> wrapper, Map<String, Object> params, String field) {
        return fillLike(wrapper, params, field, StringUtils.camelToUnderline(field));
    }

    public static <T> QueryWrapper<T> fillSelect(QueryWrapper<T> wrapper, Map<String, Object> params) {
        String sqlSelects = MapUtils.getString(params, Constant.SQL_SELECT);
        if (StringUtils.isBlank(sqlSelects)) {
            return wrapper;
        }
        return wrapper.select(sqlSelects);
    }

    public static <T> QueryWrapper<T> fillInList(QueryWrapper<T> wrapper, Map<String, Object> params, String key,
            String column) {
        Object itemJson = params.get(key);
        if (itemJson == null || StringUtils.isBlank(itemJson.toString())) {
            return wrapper;
        }

        List<String> items = JSONUtils.parseArray(itemJson, String.class);
        // 包含key，如果list为空时返回不存在
        if (CollectionUtils.isEmpty(items)) {
            return wrapper.apply("1<>1");
        }
        return wrapper.in(column, items);
    }

    public static <T> QueryWrapper<T> gtCurrentTime(QueryWrapper<T> wrapper, String column) {
        return wrapper.gt(column, "CURRENT_TIMESTAMP");
    }

    public static <T> QueryWrapper<T> ltCurrentTime(QueryWrapper<T> wrapper, String column) {
        return wrapper.lt(column, "CURRENT_TIMESTAMP");
    }

    public static <T> QueryWrapper<T> geCurrentTime(QueryWrapper<T> wrapper, String column) {
        return wrapper.ge(column, "CURRENT_TIMESTAMP");
    }

    public static <T> QueryWrapper<T> leCurrentTime(QueryWrapper<T> wrapper, String column) {
        return wrapper.le(column, "CURRENT_TIMESTAMP");
    }

    public static <T> QueryWrapper<T> fillGroupBy(QueryWrapper<T> wrapper, Map<String, Object> params) {
        String groupBy = MapUtils.getString(params, Constant.GROUP_BY);
        return wrapper.groupBy(StringUtils.isNotBlank(groupBy), groupBy);
    }

    public static <T> QueryWrapper<T> fillBetween(QueryWrapper<T> wrapper, Map<String, Object> params, String startKey,
            String stopKey, String column) {
        String begin = MapUtils.getString(params, startKey);
        String end = MapUtils.getString(params, stopKey);
        wrapper.ge(StringUtils.isNotBlank(begin), column, begin);
        wrapper.le(StringUtils.isNotBlank(end), column, end);
        return wrapper;
    }

    public static <T> QueryWrapper<T> fillDatetimeBetween(QueryWrapper<T> wrapper, Map<String, Object> params,
            String startKey, String stopKey, String column) {
        String begin = MapUtils.getString(params, startKey);
        String end = MapUtils.getString(params, stopKey);
        if (StringUtils.isNotBlank(begin) && begin.length() == 10) {
            params.put(startKey, begin + " 00:00:00");
        }
        if (StringUtils.isNotBlank(end) && end.length() == 10) {
            params.put(stopKey, end + " 23:59:59");
        }

        return fillBetween(wrapper, params, startKey, stopKey, column);
    }

    public static <T> QueryWrapper<T> fillDateBetween(QueryWrapper<T> wrapper, Map<String, Object> params,
            String startKey, String stopKey, String column) {
        String begin = MapUtils.getString(params, startKey);
        String end = MapUtils.getString(params, stopKey);
        if (StringUtils.isNotBlank(begin) && begin.length() > 10) {
            params.put(startKey, begin.substring(0, 10));
        }
        if (StringUtils.isNotBlank(end) && end.length() > 10) {
            params.put(stopKey, end.substring(0, 10));
        }

        return fillBetween(wrapper, params, startKey, stopKey, column);
    }

    public static <T> QueryWrapper<T> fillStates(QueryWrapper<T> wrapper, Map<String, Object> params) {
        String states = MapUtils.getString(params, "states");

        if (StringUtils.isBlank(states)) {
            return wrapper;
        }

        return wrapper.in("state", Arrays.asList(states.split(",")));
    }

    public static <T> QueryWrapper<T> fillCreatedAtBetween(QueryWrapper<T> wrapper, Map<String, Object> params) {
        return fillDatetimeBetween(wrapper, params, Constant.CREATED_AT_START, Constant.CREATED_AT_STOP, "created_at");
    }

    public static <T> QueryWrapper<T> fillCreatedTimeAtBetween(QueryWrapper<T> wrapper, Map<String, Object> params) {
        String begin = MapUtils.getString(params, Constant.CREATED_TIME_AT_START);
        String end = MapUtils.getString(params, Constant.CREATED_TIME_AT_STOP);
        String field = "created_time_at";

        if (StringUtils.isBlank(begin) || StringUtils.isBlank(end)) {
            return wrapper;
        }
        if (begin.equals(end)) {
            return wrapper.eq(field, begin);
        }

        LocalTime beginTime = LocalTime.parse(begin, DateTimeFormatter.ofPattern(Constant.TIME_PATTERN));
        LocalTime endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern(Constant.TIME_PATTERN));

        if (beginTime.isBefore(endTime)) {
            return fillBetween(wrapper, params, Constant.CREATED_TIME_AT_START, Constant.CREATED_TIME_AT_STOP, field);
        }

        // @formatter:off
        return wrapper.and(w1 -> w1
                .ge(field, begin).le(field, "23:59:59")
                .or()
                .ge(field, "00:00:00").le(field, end));
        // @formatter:on
    }

    public static <T> QueryWrapper<T> fillOrderBy(QueryWrapper<T> wrapper, Map<String, Object> params) {
        String asc = MapUtils.getString(params, Constant.ASC);
        String desc = MapUtils.getString(params, Constant.DESC);

        wrapper.orderByAsc(StringUtils.isNotBlank(asc), asc);
        wrapper.orderByDesc(StringUtils.isNotBlank(desc), desc);
        return wrapper;
    }
}
