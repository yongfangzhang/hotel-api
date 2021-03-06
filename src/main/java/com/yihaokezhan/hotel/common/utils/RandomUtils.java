package com.yihaokezhan.hotel.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.uuid.Generators;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class RandomUtils {

    public static String randomString32() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public static String randomString64() {
        return RandomStringUtils.randomAlphanumeric(64);
    }

    public static String uuid() {
        String uuid = "";
        // 加同步锁
        synchronized (uuid) {
            uuid = Generators.randomBasedGenerator().generate().toString();
        }
        return uuid;
    }

    public static String timeBasedUuid() {
        String uuid = "";
        // 加同步锁
        synchronized (uuid) {
            uuid = Generators.timeBasedGenerator().generate().toString();
        }
        String[] pos = uuid.split("-");
        return pos[2] + pos[1] + pos[0] + pos[3] + pos[4];
    }

    public static String generateNumer() {
        LocalDateTime datetime = LocalDateTime.now();
        String fmtTime = datetime.format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_PATTERN_NUMBER));
        String identifier = RandomStringUtils.randomNumeric(5);
        return String.format("T%s%s", fmtTime, identifier);
    }
}
