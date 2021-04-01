package com.yihaokezhan.hotel.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Component
@Slf4j
public class DailyStatisticsSchedule {

    /**
     * 凌晨0点统计订单
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void run() {
        log.info("开始统计订单信息");
        // TODO: 更新房间, 公寓收益
        
    }
}
