package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.DailyStatistics;
import com.yihaokezhan.hotel.module.mapper.DailyStatisticsMapper;
import com.yihaokezhan.hotel.module.service.IDailyStatisticsService;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = DailyStatistics.TABLE_NAME)
public class DailyStatisticsServiceImpl extends BaseServiceImpl<DailyStatisticsMapper, DailyStatistics>
        implements IDailyStatisticsService {

    @Override
    public QueryWrapper<DailyStatistics> getWrapper(Map<String, Object> params) {
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<DailyStatistics>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "channel");
        WrapperUtils.fillEq(wrapper, params, "statisticsDate");

        WrapperUtils.fillDateBetween(wrapper, params, "statisticsStartDate", "statisticsStopDate", "statistics_date");
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
