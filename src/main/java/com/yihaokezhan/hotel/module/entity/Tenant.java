package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 租户表
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(autoResultMap = true)
public class Tenant extends RemarkEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 租户UUID
     */
    @TableId(type = IdType.INPUT)
    private String uuid;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系人手机号
     */
    private String contactorMobile;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime updatedAt;


}
