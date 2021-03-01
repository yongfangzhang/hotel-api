package com.yihaokezhan.hotel.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import com.yihaokezhan.hotel.common.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;

public class EnumUtils {
    public static <E extends BaseEnum<E>> Map<Object, String> getEnumMap(final Class<E> enumClass) {
        final Map<Object, String> map = new LinkedHashMap<>();
        E[] es = enumClass.getEnumConstants();
        if (es == null) {
            return map;
        }
        for (final E e : es) {
            Object obj = e.getValue();
            if (isIgnoredValue(obj)) {
                continue;
            }
            map.put(obj, e.getName());
        }
        return map;
    }

    public static <E extends BaseEnum<E>> Map<String, Object> getEnum(final Class<E> enumClass) {
        final Map<String, Object> map = new LinkedHashMap<>();
        E[] es = enumClass.getEnumConstants();
        if (es == null) {
            return map;
        }
        for (final E e : es) {
            Object obj = e.getValue();
            if (isIgnoredValue(obj)) {
                continue;
            }
            map.put(e.toString(), obj);
        }
        return map;
    }

    public static <E extends BaseEnum<E>> E valueOf(Class<E> enumClass, Object value) {
        E[] es = enumClass.getEnumConstants();
        if (value == null) {
            return es[0].getUnknown();
        }
        for (final E e : es) {
            if (e.getValue().toString().equals(value.toString())) {
                return e;
            }
        }
        return es[0].getUnknown();
    }

    public static <E extends BaseEnum<E>> E nameOf(Class<E> enumClass, String name) {
        E[] es = enumClass.getEnumConstants();
        if (StringUtils.isBlank(name)) {
            return es[0].getUnknown();
        }
        for (final E e : es) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return es[0].getUnknown();
    }

    public static <E extends BaseEnum<E>> String getName(Class<E> enumClass, Object value) {
        E v = valueOf(enumClass, value);
        if (v == null) {
            return "";
        }
        return v.getName();
    }

    public static boolean isIgnoredValue(Object obj) {
        if (obj instanceof Integer) {
            Integer v = (Integer) obj;
            return v <= 0 || v >= 1000;
        } else if (obj instanceof String) {
            return "0".equals(obj.toString()) || "1000".equals(obj.toString());
        }
        return false;
    }
}
