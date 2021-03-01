package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.OrderState;
import com.yihaokezhan.hotel.common.remark.RemarkEntity;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
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
public class OrderItem extends RemarkEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "order_item";


    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    @NotBlank(message = "UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 订单UUID
     */
    @NotBlank(message = "订单不能为空", groups = AddGroup.class)
    private String orderUuid;

    /**
     * 租户UUID
     */
    @NotBlank(message = "租户不能为空", groups = AddGroup.class)
    private String tenantUuid;

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
    private String mobile;

    /**
     * 原始价格
     */
    @PositiveOrZero(message = "原始价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal originalPrice;

    /**
     * 支付价格
     */
    @PositiveOrZero(message = "支付价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal paidPrice;

    /**
     * 子订单状态
     */
    @NotNull(message = "子订单状态不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = OrderState.class, message = "子订单状态无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer state;

    /**
     * 入住时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @FutureOrPresent(message = "入住时间无效", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDateTime liveAt;

    /**
     * 离店时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    @FutureOrPresent(message = "离店时间无效", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDateTime leaveAt;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDateTime updatedAt;

    public String getStateName() {
        return EnumUtils.getName(OrderState.class, this.state);
    }

}
