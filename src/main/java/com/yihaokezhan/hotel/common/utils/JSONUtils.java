package com.yihaokezhan.hotel.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Slf4j
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

    public static <T> T parseFromFile(File file, Class<T> valueType) {
        String jsonStr = readFile(file);
        return parse(jsonStr, valueType);
    }

    public static <T> List<T> parseArrayFromFile(File file, Class<T> valueType) {
        String jsonStr = readFile(file);
        return parseArray(jsonStr, valueType);
    }

    private static String readFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return null;
            }
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            log.error("read json file error", e);
            return null;
        }
    }
}
