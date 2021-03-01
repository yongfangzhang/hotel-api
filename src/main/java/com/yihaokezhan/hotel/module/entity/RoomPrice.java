package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.RoomPriceType;
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
public class RoomPrice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "room_price";

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

    public String getTypeName() {
        return EnumUtils.getName(RoomPriceType.class, this.type);
    }
}
