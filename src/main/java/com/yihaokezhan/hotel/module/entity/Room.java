package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.handler.RoomPriceTypeHandler;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import com.yihaokezhan.hotel.model.RoomPrice;

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
public class Room extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "room";

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
     * 基础价格
     */
    @NotNull(message = "基础价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "基础价格无效", groups = { AddGroup.class, UpdateGroup.class })
    private BigDecimal price;

    @NotNull(message = "价格数组不能为空", groups = AddGroup.class)
    @Size(min = 1, message = "至少添加一种价格", groups = AddGroup.class)
    @TableField(typeHandler = RoomPriceTypeHandler.class)
    private List<RoomPrice> prices;

    /**
     * 房间状态
     */
    @EnumValue(enumClass = RoomState.class, message = "房间状态无效", canBeNull = true, groups = { AddGroup.class,
            UpdateGroup.class })
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
    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;

    public String getStateName() {
        return EnumUtils.getName(RoomState.class, this.state);
    }

    public String getName() {
        return String.format("%s-%s-%s", this.floorNumber, this.unitNumber, this.number);
    }

    public M getPriceTypeMap() {
        if (CollectionUtils.isEmpty(this.prices)) {
            return M.m();
        }
        M m = M.m();
        for (RoomPrice item : this.prices) {
            m.put(item.getType().toString(), item.getPrice());
        }
        return m;
    }

    public Room removeUpdateIgnores() {
        this.saleTimes = null;
        this.income = null;
        this.price = null;
        this.prices = null;
        return this;
    }

    public Room removeCreateIgnores() {
        this.saleTimes = null;
        this.income = null;
        return this;
    }
}
