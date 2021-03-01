package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.yihaokezhan.hotel.common.enums.RoomPriceType;
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
 * 房间价格 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "room_price", autoResultMap = true)
public class RoomPrice extends RemarkEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "room_price";


    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    @NotBlank(message = "UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 房间UUID
     */
    @NotBlank(message = "房间不能为空", groups = AddGroup.class)
    private String roomUuid;

    /**
     * 价格类型
     */
    @NotNull(message = "价格类型不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = RoomPriceType.class, message = "价格类型无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer type;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;

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

    public String getTypeName() {
        return EnumUtils.getName(RoomPriceType.class, this.type);
    }
}
