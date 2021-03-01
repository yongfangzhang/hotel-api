package com.yihaokezhan.hotel.module.entity;

import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
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
public class RoleRoute extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "role_route";

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

    @TableField(exist = false)
    private Route route;
}
