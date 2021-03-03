package com.yihaokezhan.hotel.common.utils;

import java.nio.charset.Charset;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class Constant {
    // @formatter:off
    // Header
    public static final String ACCESS_TOKEN_HEADER                = "Access-Token";
    public static final String WX_OPENID                          = "Wx-OpenId";
    public static final String TENANT_SPLIT_HEADER                = "Tenant";

    // system variable
    public static final String ROOT_TENANT                        = "00000000000000000000000000000000";
    public static final Charset CHARSET                           = Charset.forName("UTF-8");

    // cache
    public static final String CACHE_PREFIX_ACCOUNT_PERMS         = "account:perms:";
    public static final String CACHE_PREFIX_ACCOUNT_ROLES         = "account:roles:";
    public static final String CACHE_PREFIX_TOKEN_KEY             = "api:token:";
    public static final String CACHE_PREFIX_USER_TOKENS_KEY       = "api:tokens:";
    public static final String CACHE_PREFIX_CAPTCHA               = "captcha:"; 

    public static final long CACHE_DURATION_FOREVER               = -1L;
    public static final long CACHE_DURATION_CAPTCHA               = 3600L; 
    public static final long CACHE_DURATION_TOKEN                 = 43200L; // 12 * 3600 

    // date
    public static final String DATE_TIME_PATTERN                  = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEZONE                           = "GMT+08:00";

    // validate/pattern
    // 手机号
    public static final String PATTERN_MOBILE                     = "1\\d{10}";
    public static final String PATTERN_MOBILE_MSG                 = "手机号格式错误";
    // 密码
    public static final String PATTERN_PASSWORD                   = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
    public static final String PATTERN_PASSWORD_MSG               = "密码最少8位,最大20位(字母+数字)";

    // sql params
    // 前后台都可以用
    public static final String ASC                                = "asc";
    public static final String DESC                               = "desc";
    public static final String CREATED_AT_START                   = "createdAtStart";
    public static final String CREATED_AT_STOP                    = "createdAtStop";
    public static final String PAGE                               = "page";
    public static final String LIMIT                              = "limit";
    // 只允许后台用
    public static final String SQL_SELECT                         = "__sqlSelect__";
    public static final String GROUP_BY                           = "__gb__";
    // @formatter:on

}
