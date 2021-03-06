package com.yihaokezhan.hotel.module.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.DictType;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import com.yihaokezhan.hotel.model.TreeNode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统字典表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "system_dict", autoResultMap = true)
public class SystemDict extends BaseEntity implements TreeNode<SystemDict> {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "system_dict";

    /**
     * 父节点
     */
    private String parentUuid;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = AddGroup.class)
    private String code;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = DictType.class, message = "类型无效", groups = { AddGroup.class, UpdateGroup.class })
    private Integer type;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 值
     */
    @NotBlank(message = "值不能为空", groups = AddGroup.class)
    private String value;

    /**
     * 顺序
     */
    private Integer sequence;

    /**
     * 扩展字段
     */
    private String extendField;

    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private List<SystemDict> children;

    public String getTypeName() {
        return EnumUtils.getName(DictType.class, this.type);
    }

    @Override
    public String findUuid() {
        return this.getUuid();
    }

    @Override
    public String findParentUuid() {
        return this.getParentUuid();
    }

    @Override
    public void addChild(SystemDict child) {
        if (CollectionUtils.isEmpty(this.children)) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }
}
