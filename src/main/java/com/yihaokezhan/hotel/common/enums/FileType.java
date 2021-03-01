package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum FileType implements BaseEnum<FileType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知",    "unknown"),
    IMAGE                       (1,         "图片",    "image"),
    VIDEO                       (2,         "视频",    "video"),
    AUDIO                       (3,         "音频",    "audio"),
    DOCUMENT                    (4,         "文档",    "document"),
    VUE_CODE                    (5,         "VUE代码", "vue code"),
    END                         (1000,      "END", "end");
    // @formatter:on
    public static final String VALS = "1:图片, 2:视频, 3:音频, 4:文档";

    private Integer value;
    private String name;
    private String key;

    FileType(Integer v, String n, String k) {
        this.value = v;
        this.name = n;
        this.key = k;
    }

    public FileType getUnknown() {
        return UNKNOWN;
    }
}
