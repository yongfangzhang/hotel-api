package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum UserState implements BaseEnum<UserState> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    NORMAL                      (1,         "正常"),
    FORBIDDEN                   (10,        "已禁用"),
    DELETED                     (11,        "已删除"),
    NEW_REGISTED                (12,        "新注册"),
    VERIFY_FAILED               (13,        "认证失败"), 
    UNVERIFIED                  (14,        "未认证"),
    VERIFING                    (15,        "认证中"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    UserState(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public UserState getUnknown() {
        return UNKNOWN;
    }

    public static boolean isInvalid(Integer state) {
        return FORBIDDEN.getValue().compareTo(state) <= 0;
    }

    public static boolean isForbiddenOrDel(Integer state) {
        return FORBIDDEN.getValue().equals(state) || DELETED.getValue().equals(state);
    }

    public static boolean isBeginner(Integer state) {
        return NEW_REGISTED.getValue().compareTo(state) <= 0;
    }
}
