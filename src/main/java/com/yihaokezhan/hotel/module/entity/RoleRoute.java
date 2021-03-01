package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
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
 * 角色路由表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "role_route", autoResultMap = true)
public class RoleRoute extends RemarkEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "role_route";


    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    @NotBlank(message = "UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 角色ID
     */
    @NotBlank(message = "角色不能为空", groups = AddGroup.class)
    private String roleUuid;

    /**
     * 路由ID
     */
    @NotBlank(message = "路由不能为空", groups = AddGroup.class)
    private String routeUuid;

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
    private Route route;
}
