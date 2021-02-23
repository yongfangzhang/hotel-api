package com.yihaokezhan.hotel.common.exception;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public enum ErrorCode {
    // @formatter:off
    SUCCESS                         (0,         "成功"),
    FAIL                            (1,         "请求失败"),
    RECORD_EXSITED                  (2,         "该记录已存在"),

    REQ_NOT_SUPPORTED               (10,        "请求方法不支持"),
    REQ_INVALID_PARAMS              (11,        "请求参数错误"),
    REQ_API_LIMIT                   (12,        "访问太快了"),

    ACCESS_DENIED                   (401,       "认证失败"),
    AUTH_FORBIDDEN                  (403,       "权限拒绝"),
    NOT_FOUND                       (404,       "未找到指定记录");
    // @formatter:on
    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
