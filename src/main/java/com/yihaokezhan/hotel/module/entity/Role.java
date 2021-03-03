package com.yihaokezhan.hotel.module.entity;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
 * 角色表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "role", autoResultMap = true)
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "role";
    /**
     * 角色编码，后台用
     */
    @NotBlank(message = "编码不能为空", groups = AddGroup.class)
    private String code;

    /**
     * 账户类型
     */
    @NotNull(message = "账户类型不能为空", groups = AddGroup.class)
    private Integer accountType;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private List<Route> routes;
}
