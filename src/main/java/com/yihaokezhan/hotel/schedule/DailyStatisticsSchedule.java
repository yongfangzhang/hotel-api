package com.yihaokezhan.hotel.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.DailyStatistics;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.service.IDailyStatisticsService;
import com.yihaokezhan.hotel.module.service.IOrderService;
import com.yihaokezhan.hotel.module.service.ITenantService;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Component
@Slf4j
public class DailyStatisticsSchedule {

    @Autowired
    private IDailyStatisticsService dailyStatisticsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ITenantService tenantService;

    /**
     * 凌晨0点10分统计订单
     */
    @Scheduled(cron = "0 10 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void run() {
        tenantService.mList(M.m()).forEach(tenant -> {
            if (BooleanUtils.isNotTrue(tenant.getDeleted())) {
                runStatistics(tenant.getUuid(), LocalDateTime.now().minusDays(1));
            }
        });
    }

    public void runStatistics(String tenantUuid, LocalDateTime dateTime) {
        DynamicTenantHandler.setTenant(tenantUuid);
        log.info("开始统计: {}", dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));

        String startAt = dateTime.with(LocalTime.MIN).format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_PATTERN));
        String stopAt = dateTime.with(LocalTime.MAX).format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_PATTERN));

        // @formatter:off
        List<Order> orders = orderService.mList(
            M.m()
                .put(Constant.CREATED_AT_START, startAt)
                .put(Constant.CREATED_AT_STOP, stopAt)
        );
        // @formatter:on

        if (CollectionUtils.isEmpty(orders)) {
            log.info("当日无订单");
            return;
        }

        List<DailyStatistics> records = new ArrayList<>();

        Map<Integer, List<Order>> channelMap = orders.stream().collect(Collectors.groupingBy(k -> k.getChannel()));

        channelMap.entrySet().forEach(entry -> {
            Integer channel = entry.getKey();
            List<Order> channelOrders = entry.getValue();
            DailyStatistics record = new DailyStatistics();

            record.setTenantUuid(tenantUuid);
            record.setChannel(channel);
            record.setSaleTimes(channelOrders.size());
            record.setStatisticsDate(dateTime.toLocalDate());

            channelOrders.forEach(order -> {
                record.setOriginalPrice(record.getOriginalPrice().add(order.getOriginalPrice()));
                record.setPaidPrice(record.getPaidPrice().add(order.getPaidPrice()));
                record.setDeposit(record.getDeposit().add(order.getDeposit()));
                record.setDepositRefunded(record.getDepositRefunded().add(order.getDepositRefunded()));
                record.setDepositDeduction(record.getDepositDeduction().add(order.getDepositDeduction()));
                record.setProductIncome(record.getProductIncome().add(order.getProductIncome()));
            });
            records.add(record);
        });

        dailyStatisticsService.mBatchCreate(records);

        log.info("统计完成");
    }

}
