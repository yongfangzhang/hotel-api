package com.yihaokezhan.hotel.utils;

public class StringUtils {

    public static boolean isBlank(Object arg) {
        return arg == null || arg.toString().trim().length() == 0;
    }

}
