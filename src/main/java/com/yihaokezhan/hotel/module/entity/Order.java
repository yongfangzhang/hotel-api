package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.OrderChannel;
import com.yihaokezhan.hotel.common.enums.OrderState;
import com.yihaokezhan.hotel.common.enums.OrderType;
import com.yihaokezhan.hotel.common.enums.OrderUserType;
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
 * 订单表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "`order`", autoResultMap = true)
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "order";

    /**
     * 公寓UUID
     */
    @NotBlank(message = "公寓不能为空", groups = AddGroup.class)
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
     * 订单渠道
     */
    @NotNull(message = "订单渠道不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = OrderChannel.class, message = "订单渠道无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer channel;

    /**
     * 原始价格
     */
    @NotNull(message = "原始价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "原始价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal originalPrice;

    /**
     * 支付价格
     */
    @NotNull(message = "支付价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "支付价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal paidPrice;

    /**
     * 订单状态
     */
    @NotNull(message = "订单状态不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = OrderState.class, message = "订单状态无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer state;

    /**
     * 订单类型
     */
    @NotNull(message = "订单类型不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = OrderType.class, message = "订单类型无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer type;

    @NotNull(message = "用户类型不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = OrderUserType.class, message = "用户类型无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer userType;

    @EnumValue(enumClass = OrderType.class, message = "账户类型无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer accountType;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @Past(message = "支付时间无效", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDateTime paidAt;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @Past(message = "取消时间无效", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDateTime canceledAt;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @Past(message = "完成时间无效", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDateTime finishedAt;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @Past(message = "评价时间无效", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDateTime commentedAt;

    public String getChannelName() {
        return EnumUtils.getName(OrderChannel.class, this.channel);
    }

    public String getStateName() {
        return EnumUtils.getName(OrderState.class, this.state);
    }

    public String getTypeName() {
        return EnumUtils.getName(OrderType.class, this.type);
    }

    @TableField(exist = false)
    private List<OrderItem> items;
}
