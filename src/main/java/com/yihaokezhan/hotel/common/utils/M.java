package com.yihaokezhan.hotel.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.lang.NonNull;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class M extends LinkedHashMap<String, Object> {
    public static final long serialVersionUID = 1L;

    public static M m() {
        return new M();
    }

    public static M m(Map<String, Object> map) {
        M m = new M();
        m.putAll(map);
        return m;
    }

    @Override
    @NonNull
    public M put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    @NonNull
    public M putIfAbsent(String key, Object value) {
        super.putIfAbsent(key, value);
        return this;
    }

    public M sum(String key, Integer value) {
        if (value == null) {
            value = 0;
        }
        Integer old = (Integer) this.get(key);
        if (old == null) {
            this.put(key, value);
        } else {
            this.replace(key, old + value);
        }
        return this;
    }

    public M putIfNotNull(String key, Object value) {
        if (value == null) {
            return this;
        }
        return this.put(key, value);
    }

    public String getString(String k) {
        Object v = this.get(k);
        if (v == null) {
            return null;
        }
        return v.toString();
    }
}
