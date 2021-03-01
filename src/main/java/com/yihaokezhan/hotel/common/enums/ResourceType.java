package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum ResourceType implements BaseEnum<ResourceType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    ID_CARD                     (1,         "身份证"),
    BUSINESS_LICENSE            (2,         "营业执照"),
    END                         (1000,      "END");
    // @formatter:on

    private final Integer value;
    private final String name;

    ResourceType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    @Override
    public ResourceType getUnknown() {
        return UNKNOWN;
    }
}
