package com.yihaokezhan.hotel.common.handler;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

public class StringArrayTypeHandler extends ArrayTypeHandler<String> {

    @Override
    public List<String> parseArray(String json) {
        try {
            if (StringUtils.isEmpty(json)) {
                return new ArrayList<String>();
            }

            return JSON.parseArray(json, String.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
