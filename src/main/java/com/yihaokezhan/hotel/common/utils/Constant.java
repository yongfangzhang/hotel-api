package com.yihaokezhan.hotel.common.utils;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class Constant {
    public static final String ACCESS_TOKEN_HEADER = "Access-Token";
    public static final String WX_OPENID = "Wx-OpenId";

    public static final long REDIS_NOT_EXPIRE = -1L;

    public static final String USER_PERMS_PREFIX = "user:perms:";

    public static final String USER_ROLES_PREFIX = "user:roles:";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static final String PATTERN_MOBILE = "1\\d{10}";


    // 前后台都可以用
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String CREATED_AT_START = "createdAtStart";
    public static final String CREATED_AT_STOP = "createdAtStop";
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";
    // 只允许后台用
    public static final String SQL_SELECT = "__sqlSelect__";
    public static final String GROUP_BY = "__gb__";

}
