package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.OrderChannel;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 每日统计表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "daily_statistics", autoResultMap = true)
public class DailyStatistics extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "daily_statistics";

    /**
     * 租户UUID
     */
    private String tenantUuid;

    /**
     * 渠道
     */
    private Integer channel;

    /**
     * 原始价格
     */
    private BigDecimal originalPrice = BigDecimal.ZERO;

    /**
     * 支付价格
     */
    private BigDecimal paidPrice = BigDecimal.ZERO;

    /**
     * 押金
     */
    private BigDecimal deposit = BigDecimal.ZERO;

    /**
     * 已退押金
     */
    private BigDecimal depositRefunded = BigDecimal.ZERO;

    /**
     * 已扣除押金
     */
    private BigDecimal depositDeduction = BigDecimal.ZERO;

    /**
     * 销售次数
     */
    private Integer saleTimes;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = Constant.DATE_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDate statisticsDate;

    public String getChannelName() {
        return EnumUtils.getName(OrderChannel.class, this.channel);
    }

}
