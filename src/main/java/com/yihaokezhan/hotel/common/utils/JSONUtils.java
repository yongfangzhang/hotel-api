package com.yihaokezhan.hotel.common.utils;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.yihaokezhan.hotel.common.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Component
public class JSONUtils {

    public static ObjectMapper objectMapper;

    @Autowired
    public JSONUtils(ObjectMapper objectMapper) {
        JSONUtils.objectMapper = objectMapper;
    }

    public static String toJSONString(Object obj) {
        return toJSONString(null, obj);
    }

    public static String toJSONString(JsonView jv, Object obj) {
        try {
            if (jv == null || jv.value().length == 0) {
                return objectMapper.writeValueAsString(obj);
            }
            return objectMapper.writerWithView(jv.value()[0]).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RRException("解析结果失败");
        }
    }

    /**
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T parse(String jsonStr, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonStr, valueType);
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
    public static <T> List<T> parseArray(String jsonStr, Class<T> valueType) {
        try {
            CollectionType javaType =
                    objectMapper.getTypeFactory().constructCollectionType(List.class, valueType);

            return objectMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
