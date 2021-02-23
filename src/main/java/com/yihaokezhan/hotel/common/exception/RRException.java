package com.yihaokezhan.hotel.common.exception;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = ErrorCode.FAIL.getCode();

    public RRException(Exception e) {
        super(e);
        this.msg = e.getMessage();
    }

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(ErrorCode ec) {
        this(ec.getMessage(), ec.getCode());
    }

    public RRException(ErrorCode ec, String msg) {
        this(msg == null ? ec.getMessage() : msg, ec.getCode());
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
