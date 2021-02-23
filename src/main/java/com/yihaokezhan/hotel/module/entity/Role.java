package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
import java.util.List;
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
 * 角色表
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(autoResultMap = true)
public class Role extends RemarkEntity {


    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    private String uuid;

    /**
     * 角色编码，后台用
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

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

    @TableField(exist = false)
    private List<Route> routes;

}
