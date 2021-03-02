package com.yihaokezhan.hotel.common.utils;

import java.util.List;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class JSONUtils {

    public static String stringify(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return JSON.toJSONStringWithDateFormat(obj, Constant.DATE_TIME_PATTERN);
    }

    /**
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T parse(String jsonStr, Class<T> valueType) {
        try {
            if (StringUtils.isBlank(jsonStr)) {
                return null;
            }
            return JSON.parseObject(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * json数组转List
     * 
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> List<T> parseArray(Object jsonStr, Class<T> valueType) {
        try {
            String ori = stringify(jsonStr);
            if (StringUtils.isBlank(ori)) {
                return null;
            }
            return JSON.parseArray(ori, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
