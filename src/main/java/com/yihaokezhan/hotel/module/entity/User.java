package com.yihaokezhan.hotel.module.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.CustomerChannel;
import com.yihaokezhan.hotel.common.enums.Gender;
import com.yihaokezhan.hotel.common.enums.UserState;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.EnumValue;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
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
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "user";

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
    @EnumValue(enumClass = UserState.class, message = "用户状态无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer state;


    @TableField(exist = false)
    private WxUserInfo wxUserInfo;

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
