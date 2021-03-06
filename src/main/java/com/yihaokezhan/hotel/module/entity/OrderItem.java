package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.OrderState;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单详细表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "order_item", autoResultMap = true)
public class OrderItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "order_item";

    /**
     * 订单UUID
     */
    @NotBlank(message = "订单不能为空", groups = AddGroup.class)
    private String orderUuid;

    /**
     * 公寓UUID
     */
    @NotBlank(message = "公寓不能为空", groups = AddGroup.class)
    private String apartmentUuid;

    /**
     * 分配房间UUID
     */
    @NotBlank(message = "分配房间不能为空", groups = AddGroup.class)
    private String roomUuid;

    /**
     * 入住人姓名
     */
    @NotBlank(message = "入住人姓名不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 入住人手机号
     */
    @NotBlank(message = "入住人手机号不能为空", groups = AddGroup.class)
    @Pattern(regexp = Constant.PATTERN_MOBILE, message = Constant.PATTERN_MOBILE_MSG, groups = { AddGroup.class,
            UpdateGroup.class })
    private String mobile;

    /**
     * 原始价格
     */
    @PositiveOrZero(message = "原始价格无效", groups = { AddGroup.class, UpdateGroup.class })
    private BigDecimal originalPrice;

    /**
     * 支付价格
     */
    @PositiveOrZero(message = "支付价格无效", groups = { AddGroup.class, UpdateGroup.class })
    private BigDecimal paidPrice;

    /**
     * 子订单状态
     */
    @NotNull(message = "子订单状态不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = OrderState.class, message = "子订单状态无效", canBeNull = true, groups = { AddGroup.class,
            UpdateGroup.class })
    private Integer state;

    /**
     * 入住时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @FutureOrPresent(message = "入住时间无效", groups = { AddGroup.class, UpdateGroup.class })
    private LocalDateTime liveAt;

    /**
     * 离店时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @FutureOrPresent(message = "离店时间无效", groups = { AddGroup.class, UpdateGroup.class })
    private LocalDateTime leaveAt;

    public String getStateName() {
        return EnumUtils.getName(OrderState.class, this.state);
    }

}
