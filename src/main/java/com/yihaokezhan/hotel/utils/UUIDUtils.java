package com.yihaokezhan.hotel.utils;

import com.fasterxml.uuid.Generators;

public class UUIDUtils {

    public static String generate() {
        String uuid = "";
        // 加同步锁
        synchronized (uuid) {
            uuid = Generators.randomBasedGenerator().generate().toString();
        }
        return uuid;
    }

    public static String generateTimeBased() {
        String uuid = "";
        // 加同步锁
        synchronized (uuid) {
            uuid = Generators.timeBasedGenerator().generate().toString();
        }
        String[] pos = uuid.split("-");
        return pos[2] + pos[1] + pos[0] + pos[3] + pos[4];
    }
}
