package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
public enum Operation implements BaseEnum<Operation> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    CREATE                      (1,         "创建"),
    RETRIEVE                    (2,         "查看"),
    UPDATE                      (3,         "更新"),
    DELETE                      (4,         "删除"),
    LOGIN                       (100,       "登录"),
    LOGOUT                      (101,       "退出"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    Operation(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    @Override
    public Operation getUnknown() {
        return UNKNOWN;
    }
}
