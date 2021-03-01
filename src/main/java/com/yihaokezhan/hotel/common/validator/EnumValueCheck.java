package com.yihaokezhan.hotel.common.validator;

import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhangyongfang
 * @since Mon Mar 01 2021
 */

@Slf4j
public class EnumValueCheck {

    public static boolean checkValue(Class<? extends Enum<?>> enumClass, String valueMethod,
            Object value) {
        try {
            Method method = enumClass.getDeclaredMethod(valueMethod);
            for (Object type : enumClass.getEnumConstants()) {
                if (method.invoke(type).equals(value)) {
                    return true;
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
