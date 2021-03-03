package com.yihaokezhan.hotel.common.handler;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import org.apache.commons.lang3.StringUtils;

public class RemarkTypeHandler extends ArrayTypeHandler<RemarkRecord> {

    @Override
    public List<RemarkRecord> parseArray(String json) {
        try {
            if (StringUtils.isEmpty(json)) {
                return new ArrayList<RemarkRecord>();
            }

            return JSON.parseArray(json, RemarkRecord.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
