package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
 * 租户表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "tenant", autoResultMap = true)
public class Tenant extends RemarkEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "tenant";


    /**
     * 租户UUID
     */
    @TableId(type = IdType.INPUT)
    @TableField(fill = FieldFill.INSERT)
    @NotBlank(message = "租户UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 法人
     */
    @NotBlank(message = "法人不能为空", groups = AddGroup.class)
    private String legalPerson;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空", groups = AddGroup.class)
    private String contactor;

    /**
     * 联系人手机号
     */
    @NotBlank(message = "联系人手机号不能为空", groups = AddGroup.class)
    @Pattern(regexp = Constant.PATTERN_MOBILE, message = "手机号格式错误",
            groups = {AddGroup.class, UpdateGroup.class})
    private String contactorMobile;

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
