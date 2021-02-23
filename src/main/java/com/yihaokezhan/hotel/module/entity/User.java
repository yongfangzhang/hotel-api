package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
import java.util.List;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihaokezhan.hotel.common.remark.RemarkEntity;
import com.yihaokezhan.hotel.common.utils.Constant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends RemarkEntity {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    private String uuid;

    /**
     * 所属租户
     */
    private String tenantUuid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 微信UnionId
     */
    private String unionId;

    /**
     * 微信OpenId
     */
    private String openId;

    /**
     * 微信头像URL
     */
    private String avatarUrl;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 语言
     */
    private String language;

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
    private List<UserRole> userRole;

}
