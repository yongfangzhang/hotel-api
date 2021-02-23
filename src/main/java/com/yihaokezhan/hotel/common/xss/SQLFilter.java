package com.yihaokezhan.hotel.common.xss;

import com.yihaokezhan.hotel.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;


/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class SQLFilter {

    /**
     * 非法字符
     */
    private static final String[] KEYWORDS = {"master", "truncate", "insert", "select", "delete",
            "update", "declare", "alter", "drop"};

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // 去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        // 转换成小写
        str = str.toLowerCase();

        // 判断是否包含非法字符
        for (String keyword : KEYWORDS) {
            if (str.contains(keyword)) {
                throw new RRException("包含非法字符");
            }
        }

        return str;
    }
}
