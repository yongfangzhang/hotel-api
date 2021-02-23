package com.yihaokezhan.hotel.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ch.hsr.geohash.GeoHash;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class GeoUtils {

    // geo length | width | height |
    // 1 | 5009.4km | 4992.6km |
    // 2 | 1252.3km | 624.1km |
    // 3 | 156.5km | 156km |
    // 4 | 39.1km | 19.5km |
    // 5 | 4.9km | 4.9km |
    // 6 | 1.2km | 609.4m |

    private static final int GEO_CHAR_LEN = 5;

    private static double earthRadius = 6367000.0;


    public static String geohash(double latitude, double longitude) {
        return GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, GEO_CHAR_LEN);
    }

    public static List<String> geohashWithNeighbors(double latitude, double longitude) {
        GeoHash geohash = GeoHash.withCharacterPrecision(latitude, longitude, GEO_CHAR_LEN);
        List<String> hashes = Arrays.stream(geohash.getAdjacent()).map(GeoHash::toBase32)
                .collect(Collectors.toList());
        hashes.add(geohash.toBase32());
        return hashes;
    }

    public static double distance(double lat1, double lng1, double lat2, double lng2) {
        double dx = lng1 - lng2; // 经度差值
        double dy = lat1 - lat2; // 纬度差值
        double b = (lat1 + lat2) / 2.0; // 平均纬度
        double Lx = Math.toRadians(dx) * earthRadius * Math.cos(Math.toRadians(b)); // 东西距离
        double Ly = earthRadius * Math.toRadians(dy); // 南北距离
        return Math.sqrt(Lx * Lx + Ly * Ly); // 用平面的矩形对角距离公式计算总距离
    }
}
