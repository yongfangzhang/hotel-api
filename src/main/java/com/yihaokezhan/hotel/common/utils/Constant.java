package com.yihaokezhan.hotel.common.utils;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class Constant {
    public static final String ACCESS_TOKEN_HEADER = "Access-Token";
    public static final String WX_OPENID = "Wx-OpenId";
    public static final String TENANT_SPLIT_HEADER = "Tenant";

    public static final String ROOT_TENANT = "00000000000000000000000000000000";



    public static final long REDIS_NOT_EXPIRE = -1L;

    public static final String ACCOUNT_PERMS_PREFIX = "account:perms:";

    public static final String ACCOUNT_ROLES_PREFIX = "account:roles:";

    public static final String TOKEN_KEY_PREFIX = "api:token:";
    public static final String USER_TOKENS_KEY_PREFIX = "api:tokens:";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEZONE = "GMT+08:00";


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


    public static final String CACHE_PREFIX_QUERY = "query";
    public static final String CACHE_PREFIX_PAGE = CACHE_PREFIX_QUERY + "::page::";
    public static final String CACHE_PREFIX_LIST = CACHE_PREFIX_QUERY + "::list::";
    public static final String CACHE_PREFIX_ONE = CACHE_PREFIX_QUERY + "::one::";

}
