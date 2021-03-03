package com.yihaokezhan.hotel.common.validator;

import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import org.springframework.lang.Nullable;

public class Assert {

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new RRException(message);
        }
    }

    public static void state(boolean expression, ErrorCode errCode) {
        if (!expression) {
            throw new RRException(errCode);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new RRException(message);
        }
    }

    public static void isTrue(boolean expression, ErrorCode errCode) {
        if (!expression) {
            throw new RRException(errCode);
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new RRException(message);
        }
    }

    public static void isNull(@Nullable Object object, ErrorCode errCode) {
        if (object != null) {
            throw new RRException(errCode);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }

    public static void notNull(@Nullable Object object, ErrorCode errCode) {
        if (object == null) {
            throw new RRException(errCode);
        }
    }
}

