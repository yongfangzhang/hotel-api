package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.remark.RemarkEntity;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(autoResultMap = true)
public class Order extends RemarkEntity {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    private String uuid;

    /**
     * 租户UUID
     */
    private String tenantUuid;

    /**
     * 公寓UUID
     */
    private String apartmentUuid;

    /**
     * 用户UUID
     */
    private String userUuid;

    /**
     * 订单号
     */
    private String number;

    /**
     * 订单来源
     */
    private Integer from;

    /**
     * 原始价格
     */
    private BigDecimal originalPrice;

    /**
     * 支付价格
     */
    private BigDecimal paidPrice;

    /**
     * 订单状态
     */
    private Integer state;

    /**
     * 订单类型
     */
    private Integer type;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime paidAt;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime updatedAt;


}
