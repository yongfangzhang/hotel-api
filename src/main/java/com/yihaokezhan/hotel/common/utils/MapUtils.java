package com.yihaokezhan.hotel.common.utils;

import java.util.Map;

public class MapUtils {
    public static String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) {
            return null;
        }
        return val.toString();
    }
}
