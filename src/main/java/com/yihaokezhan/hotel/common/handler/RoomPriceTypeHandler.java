package com.yihaokezhan.hotel.common.handler;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.yihaokezhan.hotel.model.RoomPrice;
import org.apache.commons.lang3.StringUtils;

public class RoomPriceTypeHandler extends ArrayTypeHandler<RoomPrice> {

    @Override
    public List<RoomPrice> parseArray(String json) {
        try {
            if (StringUtils.isEmpty(json)) {
                return new ArrayList<RoomPrice>();
            }

            return JSON.parseArray(json, RoomPrice.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
