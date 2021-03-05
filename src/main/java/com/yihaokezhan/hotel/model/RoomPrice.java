package com.yihaokezhan.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import com.yihaokezhan.hotel.common.enums.RoomPriceType;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomPrice implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6083443997967417449L;

    @NotNull(message = "价格类型不能为空")
    @EnumValue(enumClass = RoomPriceType.class, message = "价格类型无效",
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer type;

    @NotNull(message = "价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;
}
