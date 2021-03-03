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
    CAPTCHA_FAILED                  (13,        "图片验证码错误"),

    ACCOUNT_PASSWORD_ERROR          (23,        "账号或密码错误"),
    ACCOUNT_NOT_FOUND               (24,        "账号不存在"),
    USER_NOT_FOUND                  (25,        "用户不存在"),
    TENANT_NOT_FOUND                (26,        "租户不存在"),
    ACCOUNT_FORBIDDEN               (27,        "账号已禁用"),
    USER_FORBIDDEN                  (28,        "用户已禁用"),
    TENANT_FORBIDDEN                (29,        "租户已禁用"),

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
