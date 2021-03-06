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
    public static final String[] NO_TENANT_REG                    = new String[] {
        ROOT_TENANT,
        "^comm_.*$",
        "role",
        "role_route",
        "route",
        "tenant",
        "system_dict"
    };

    // cache
    public static final String CACHE_PREFIX_SHRIO                 = "shiro::";
    public static final String CACHE_PREFIX_TOKEN_KEY             = "api:token:";
    public static final String CACHE_PREFIX_USER_TOKENS_KEY       = "api:tokens:";
    public static final String CACHE_PREFIX_CAPTCHA               = "captcha:"; 

    public static final long CACHE_DURATION_FOREVER               = -1L;
    public static final long CACHE_DURATION_CAPTCHA               = 3600L; 
    public static final long CACHE_DURATION_TOKEN                 = 43200L; // 12 * 3600 

    // date
    public static final String DATE_PATTERN                       = "yyyy-MM-dd";
    public static final String TIME_PATTERN                       = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN                  = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_BEGIN            = "yyyy-MM-dd 00:00:00";
    public static final String DATE_TIME_PATTERN_END              = "yyyy-MM-dd 23:59:59";
    public static final String DATE_TIME_PATTERN_NUMBER           = "yyyyMMddHHmmss";
    public static final String TIMEZONE                           = "GMT+08:00";

    // validate/pattern
    // ?????????
    public static final String PATTERN_MOBILE                     = "1\\d{10}";
    public static final String PATTERN_MOBILE_MSG                 = "?????????????????????";
    // ??????
    public static final String PATTERN_PASSWORD                   = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
    public static final String PATTERN_PASSWORD_MSG               = "????????????8???,??????20???(??????+??????)";

    // sql params
    // ?????????????????????
    public static final String ASC                                = "asc";
    public static final String DESC                               = "desc";
    public static final String CREATED_AT_START                   = "createdAtStart";
    public static final String CREATED_AT_STOP                    = "createdAtStop";
    public static final String CREATED_TIME_AT_START              = "createdTimeAtStart";
    public static final String CREATED_TIME_AT_STOP               = "createdTimeAtStop";

    public static final String PAGE                               = "page";
    public static final String LIMIT                              = "limit";
    // ??????????????????
    public static final String SQL_SELECT                         = "__sqlSelect__";
    public static final String GROUP_BY                           = "__gb__";


    // permissions
    
    // EnumController
    
    // ResourceController
    
    // CaptchaController
    
    // CommAreaController
    
    // CommCityController
    
    // CommProvinceController
    
    // PassportController

    // AccountController
    public static final String PERM_ACCOUNT_GET                   = "account:get";
    public static final String PERM_ACCOUNT_CREATE                = "account:create";
    public static final String PERM_ACCOUNT_UPDATE                = "account:update";
    public static final String PERM_ACCOUNT_DELETE                = "account:delete";
    
    // AccountRoleController
    public static final String PERM_ACCOUNT_ROLE_GET              = "account:role:get";
    public static final String PERM_ACCOUNT_ROLE_CREATE           = "account:role:create";
    public static final String PERM_ACCOUNT_ROLE_UPDATE           = "account:role:update";
    public static final String PERM_ACCOUNT_ROLE_DELETE           = "account:role:delete";
    
    // RoleController
    public static final String PERM_ROLE_GET                      = "role:get";
    public static final String PERM_ROLE_CREATE                   = "role:create";
    public static final String PERM_ROLE_UPDATE                   = "role:update";
    public static final String PERM_ROLE_DELETE                   = "role:delete";
    
    // RoleRouteController
    public static final String PERM_ROLE_ROUTE_GET                = "role:route:get";
    public static final String PERM_ROLE_ROUTE_CREATE             = "role:route:create";
    public static final String PERM_ROLE_ROUTE_UPDATE             = "role:route:update";
    public static final String PERM_ROLE_ROUTE_DELETE             = "role:route:delete";
    
    // RouteController
    public static final String PERM_ROUTE_GET                     = "route:get";
    public static final String PERM_ROUTE_CREATE                  = "route:create";
    public static final String PERM_ROUTE_UPDATE                  = "route:update";
    public static final String PERM_ROUTE_DELETE                  = "route:delete";

    // ApartmentController
    public static final String PERM_APARTMENT_GET                 = "apartment:get";
    public static final String PERM_APARTMENT_CREATE              = "apartment:create";
    public static final String PERM_APARTMENT_UPDATE              = "apartment:update";
    public static final String PERM_APARTMENT_DELETE              = "apartment:delete";
    
    // RoomController
    public static final String PERM_ROOM_GET                      = "room:get";
    public static final String PERM_ROOM_CREATE                   = "room:create";
    public static final String PERM_ROOM_UPDATE                   = "room:update";
    public static final String PERM_ROOM_DELETE                   = "room:delete";
    
    // RoomPriceController
    public static final String PERM_ROOM_PRICE_GET                = "room:price:get";
    public static final String PERM_ROOM_PRICE_CREATE             = "room:price:create";
    public static final String PERM_ROOM_PRICE_UPDATE             = "room:price:update";
    public static final String PERM_ROOM_PRICE_DELETE             = "room:price:delete";

    // OrderController
    public static final String PERM_ORDER_GET                     = "order:get";
    public static final String PERM_ORDER_CREATE                  = "order:create";
    public static final String PERM_ORDER_UPDATE                  = "order:update";
    public static final String PERM_ORDER_DELETE                  = "order:delete";

    // OrderItemController
    public static final String PERM_ORDER_ITEM_GET                = "order:item:get";
    public static final String PERM_ORDER_ITEM_CREATE             = "order:item:create";
    public static final String PERM_ORDER_ITEM_UPDATE             = "order:item:update";
    public static final String PERM_ORDER_ITEM_DELETE             = "order:item:delete";

    // TenantController
    public static final String PERM_TENANT_GET                    = "tenant:get";
    public static final String PERM_TENANT_CREATE                 = "tenant:create";
    public static final String PERM_TENANT_UPDATE                 = "tenant:update";
    public static final String PERM_TENANT_DELETE                 = "tenant:delete";

    // SystemHttpLogController
    // SystemLogController
    public static final String PERM_SYSTEM_LOG_GET                = "system:log:get";
    public static final String PERM_SYSTEM_LOG_CREATE             = "system:log:create";
    public static final String PERM_SYSTEM_LOG_UPDATE             = "system:log:update";
    public static final String PERM_SYSTEM_LOG_DELETE             = "system:log:delete";

    // SystemDictController
    public static final String PERM_SYSTEM_DICT_GET                = "system:dict:get";
    public static final String PERM_SYSTEM_DICT_CREATE             = "system:dict:create";
    public static final String PERM_SYSTEM_DICT_UPDATE             = "system:dict:update";
    public static final String PERM_SYSTEM_DICT_DELETE             = "system:dict:delete";

    // @formatter:on

}
