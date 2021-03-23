package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum CustomerChannel implements BaseEnum<CustomerChannel> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    MEITUAN                     (1,         "美团"),
    WECHAT                      (2,         "微信"),
    ALIPAY                      (3,         "支付宝"),
    STORE                       (4,         "门店"),
    BACKEND                     (10,        "后台添加"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    CustomerChannel(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public CustomerChannel getUnknown() {
        return UNKNOWN;
    }
}
