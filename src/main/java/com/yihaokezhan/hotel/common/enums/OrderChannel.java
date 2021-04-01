package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum OrderChannel implements BaseEnum<OrderChannel> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    MEITUAN                     (1,         "美团"),
    WECHAT                      (2,         "微信"),
    ALIPAY                      (3,         "支付宝"),
    STORE                       (4,         "门店"),
    XIE_CHENG                   (5,         "携程"),
    ADMIN                       (10,        "代下单"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    OrderChannel(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public OrderChannel getUnknown() {
        return UNKNOWN;
    }
}
