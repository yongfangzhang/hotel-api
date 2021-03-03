package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum CustomerChannel implements BaseEnum<CustomerChannel> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    BACKEND                     (1,         "后台添加"),
    MEITUAN                     (2,         "美团"),
    WECHAT                      (3,         "微信"),
    ALIPAY                      (4,         "支付宝"),
    STORE                       (5,         "门店"),
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
