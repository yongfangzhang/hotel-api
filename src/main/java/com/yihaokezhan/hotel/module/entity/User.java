package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.CustomerChannel;
import com.yihaokezhan.hotel.common.enums.Gender;
import com.yihaokezhan.hotel.common.enums.UserState;
import com.yihaokezhan.hotel.common.remark.RemarkEntity;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 * 用户表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "user", autoResultMap = true)
public class User extends RemarkEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "user";


    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    @NotBlank(message = "UUID不能为空", groups = UpdateGroup.class)
    private String uuid;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = Gender.class, message = "性别无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer gender;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = AddGroup.class)
    private String mobile;

    /**
     * 微信UUID
     */
    private String wxUuid;

    /**
     * 获客渠道
     */
    @NotNull(message = "获客渠道不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = CustomerChannel.class, message = "获客渠道无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer channel;

    /**
     * 用户状态
     */
    @NotNull(message = "用户状态不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = UserState.class, message = "用户状态无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer state;

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

    public String getGenderName() {
        return EnumUtils.getName(Gender.class, this.gender);
    }

    public String getChannelName() {
        return EnumUtils.getName(CustomerChannel.class, this.channel);
    }

    public String getStateName() {
        return EnumUtils.getName(UserState.class, this.state);
    }
}
