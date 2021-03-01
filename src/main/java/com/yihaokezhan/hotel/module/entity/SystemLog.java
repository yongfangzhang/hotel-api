package com.yihaokezhan.hotel.module.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 系统日志 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonView(V.S.class)
@TableName(value = "system_log", autoResultMap = true)
public class SystemLog implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "system_log";

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 租户UUID
     */
    private String tenantUuid;
    /**
     * 账号UUID
     */
    private String accountUuid;
    /**
     * 账号名称
     */
    private String accountName;
    /**
     * 账号类型
     */
    private Integer accountType;
    /**
     * 级别
     */
    private Boolean level;
    /**
     * 操作目标
     */
    private Integer target;
    /**
     * 操作类型
     */
    private Integer type;
    /**
     * 关联UUID
     */
    private String linked;
    /**
     * IP
     */
    private String ip;
    /**
     * 内容
     */
    private String content;
    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDateTime createdAt;
}
