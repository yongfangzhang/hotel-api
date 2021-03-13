package com.yihaokezhan.hotel.common.utils;

import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

public class MapUtils {
    public static String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) {
            return null;
        }
        return val.toString();
    }

    public static boolean getBoolean(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) {
            return false;
        }
        return BooleanUtils.toBoolean(val.toString());
    }

}
