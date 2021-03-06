package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.ApartmentState;
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
 * 公寓表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "apartment", autoResultMap = true)
public class Apartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "apartment";

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 简称
     */
    @NotBlank(message = "简称不能为空", groups = AddGroup.class)
    private String shortName;

    /**
     * 县区
     */
    @NotBlank(message = "县区不能为空", groups = AddGroup.class)
    private String areaCode;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空", groups = AddGroup.class)
    private String address;

    /**
     * 经度
     */
    @NotNull(message = "经度不能为空", groups = AddGroup.class)
    private Double longitude;

    /**
     * 维度
     */
    @NotNull(message = "维度不能为空", groups = AddGroup.class)
    private Double latitude;

    /**
     * geohash4
     */
    private String geohash4;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空", groups = AddGroup.class)
    private String contactor;

    /**
     * 联系人手机号
     */
    @NotBlank(message = "联系人手机号不能为空", groups = AddGroup.class)
    @Pattern(regexp = Constant.PATTERN_MOBILE, message = Constant.PATTERN_MOBILE_MSG, groups = { AddGroup.class,
            UpdateGroup.class })
    private String contactorMobile;

    /**
     * 公寓状态
     */
    @NotNull(message = "公寓状态不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = ApartmentState.class, message = "公寓状态无效", canBeNull = true, groups = { AddGroup.class,
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
        return EnumUtils.getName(ApartmentState.class, this.state);
    }

    public Apartment removeIgnores() {
        this.saleTimes = null;
        this.income = null;
        return this;
    }
}
