package com.yihaokezhan.hotel.common.utils;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class V {
    public interface IGNORE {
    }

    /* 超小字段 */
    public interface XS {
    }

    /* 小字段 */
    public interface S extends XS {
    }

    /* 正常字段 */
    public interface F extends S {
    }

    /* 详细字段 */
    public interface FD extends F {
    }
}
