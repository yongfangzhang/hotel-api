package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
import com.yihaokezhan.hotel.common.enums.RoomState;
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
 * 房间表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "room", autoResultMap = true)
public class Room extends RemarkEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "room";


    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    @NotBlank(message = "UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 公寓UUID
     */
    @NotBlank(message = "公寓不能为空", groups = AddGroup.class)
    private String apartmentUuid;

    /**
     * 房间类型
     */
    @NotBlank(message = "房间类型不能为空", groups = AddGroup.class)
    private String typeUuid;

    /**
     * 楼号
     */
    @NotBlank(message = "楼号不能为空", groups = AddGroup.class)
    private String floorNumber;

    /**
     * 单元号
     */
    @NotBlank(message = "单元号不能为空", groups = AddGroup.class)
    private String unitNumber;

    /**
     * 房间号
     */
    @NotBlank(message = "房间号不能为空", groups = AddGroup.class)
    private String number;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;

    /**
     * 房间状态
     */
    @NotNull(message = "房间状态不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = RoomState.class, message = "房间状态无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer state;

    /**
     * 销售次数
     */
    private Integer saleTimes;

    /**
     * 总收益
     */
    private BigDecimal income;

    /**
     * 是否已删除
     */
    private Boolean deleted;

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

    @TableField(exist = false)
    private List<RoomPrice> prices;

    public String getStateName() {
        return EnumUtils.getName(RoomState.class, this.state);
    }

}
