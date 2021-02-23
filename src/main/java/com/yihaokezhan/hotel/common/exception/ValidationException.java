package com.yihaokezhan.hotel.common.exception;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class ValidationException extends RRException {
    public static final long serialVersionUID = 1L;

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable e) {
        super(msg, e);
    }

    public ValidationException(String msg, int code) {
        super(msg, code);
    }

    public ValidationException(String msg, int code, Throwable e) {
        super(msg, code, e);
    }
}
