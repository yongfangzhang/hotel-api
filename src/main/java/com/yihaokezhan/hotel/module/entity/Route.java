package com.yihaokezhan.hotel.module.entity;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.RouteType;
import com.yihaokezhan.hotel.common.handler.StringArrayTypeHandler;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 * 路由表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "route", autoResultMap = true)
public class Route extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "route";

    /**
     * 路由
     */
    @NotBlank(message = "路由不能为空", groups = AddGroup.class)
    private String path;

    /**
     * 账户类型
     */
    @NotNull(message = "账户类型不能为空", groups = AddGroup.class)
    private Integer accountType;

    /**
     * 路由类型(菜单、按钮)
     */
    private Integer type;

    /**
     * 权限列表(逗号分割)
     */
    @TableField(typeHandler = StringArrayTypeHandler.class)
    @NotNull(message = "权限列表不能为空", groups = AddGroup.class)
    @Size(min = 1, message = "权限列表无效", groups = {AddGroup.class, UpdateGroup.class})
    private List<String> permissions;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空", groups = AddGroup.class)
    private String caption;

    /**
     * 描述
     */
    private String description;

    public String getTypeName() {
        return EnumUtils.getName(RouteType.class, this.type);
    }
}
