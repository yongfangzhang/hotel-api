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
 * 网络日志 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonView(V.S.class)
@TableName(value = "system_http_log", autoResultMap = true)
public class SystemHttpLog implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "system_http_log";

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
     * 模块
     */
    private Integer module;
    /**
     * 路径
     */
    private String path;
    /**
     * 请求header
     */
    private String headers;
    /**
     * 请求query参数
     */
    private String parameterMap;
    /**
     * 方法
     */
    private String method;
    /**
     * 时长
     */
    private Long duration;
    /**
     * 时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDateTime time;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 请求body
     */
    @JsonView(V.F.class)
    private String requestBody;
    /**
     * 响应body
     */
    @JsonView(V.F.class)
    private String responseBody;
    /**
     * IP
     */
    private String ip;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDateTime createdAt;
}
