package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
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
public class Apartment extends RemarkEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "apartment";


    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    @NotBlank(message = "公寓UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 租户UUID
     */
    @NotBlank(message = "租户UUID不能为空", groups = AddGroup.class)
    private String tenantUuid;

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
    private String contactorMobile;

    /**
     * 公寓状态
     */
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
}
