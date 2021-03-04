package com.yihaokezhan.hotel.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
@Setter
@JsonView(V.XS.class)
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(List<String> errors) {
        return error(500, errors);
    }

    public static R error(ErrorCode ec) {
        return error(ec, null);
    }

    public static R error(ErrorCode ec, String msg) {
        return error(ec.getCode(), msg == null ? ec.getMessage() : msg);
    }

    public static R errors(ErrorCode ec, List<String> errors) {
        return error(ec.getCode(), errors);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(int code, List<String> errors) {
        R r = new R();
        r.put("code", code);
        r.put("msg", CollectionUtils.isEmpty(errors) ? "未知异常，请联系管理员" : errors.get(0));
        r.put("errors", errors);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R data(Object data) {
        return this.put("data", data);
    }

    public boolean isSuccess() {
        Integer successCode = ErrorCode.SUCCESS.getCode();
        Object code = this.get("code");
        return successCode.equals(code) || successCode.toString().equals(code);
    }

    @Override
    public String toString() {
        return JSONUtils.stringify(this);
    }
}
