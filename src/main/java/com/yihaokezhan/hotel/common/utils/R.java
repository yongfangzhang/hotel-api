package com.yihaokezhan.hotel.common.utils;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
@Setter
@JsonView(V.XS.class)
@Slf4j
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code = ErrorCode.SUCCESS.getCode();

    private String msg = ErrorCode.SUCCESS.getMessage();

    private T data;

    private Boolean success;

    public R() {
    }

    public R(T d) {
        this.data = d;
    }

    public R(String msg) {
        this.msg = msg;
    }

    public R(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public R(ErrorCode err) {
        this.msg = err.getMessage();
        this.code = err.getCode();
    }

    public boolean isSuccess() {
        return ErrorCode.SUCCESS.getCode().equals(this.code)
                || this.success != null && Boolean.TRUE.equals(this.success);
    }

    public void validate() {
        if (!this.isSuccess()) {
            log.error("服务调用失败: {}, {}", this.code, this.msg);
            throw new RRException(this.msg, this.code);
        }
    }

    public T getData() {
        this.validate();
        return this.data;
    }

    public static <T> R<T> error(String msg) {
        return new R<T>(msg);
    }

    public static <T> R<T> error(String msg, Integer code) {
        return new R<T>(msg, code);
    }

    public static <T> R<T> error(ErrorCode err) {
        return new R<T>(err);
    }

    public static <T> R<T> errors(ErrorCode err, List<String> errors) {
        R<T> r = new R<T>(err);
        r.setMsg(StringUtils.join(errors));
        return r;
    }

    public static <T> R<T> ok() {
        return new R<T>();
    }

    public static <T> R<T> ok(T data) {
        R<T> re = ok();
        re.setData(data);
        return re;
    }
}
