package com.yihaokezhan.hotel.module.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.AccountType;
import com.yihaokezhan.hotel.common.enums.UserState;
import com.yihaokezhan.hotel.common.utils.Constant;
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
 * 登录账号表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "account", autoResultMap = true)
public class Account extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "account";

    /**
     * 所属用户
     */
    @NotBlank(message = "用户不能为空", groups = AddGroup.class)
    private String userUuid;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空", groups = AddGroup.class)
    private String account;

    /**
     * 平台类型
     */
    @NotNull(message = "平台类型不能为空", groups = AddGroup.class)
    @EnumValue(enumClass = AccountType.class, message = "平台类型无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer type;

    /**
     * 盐值
     */
    @NotBlank(message = "盐值不能为空", groups = AddGroup.class)
    @JsonView(V.IGNORE.class)
    private String salt;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    @JsonView(V.IGNORE.class)
    private String password;

    /**
     * 账号状态
     */
    @EnumValue(enumClass = UserState.class, message = "账号状态无效", canBeNull = true,
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer state;

    /**
     * 设备
     */
    private String device;

    /**
     * UA
     */
    private String userAgent;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE)
    private LocalDateTime lastLoginAt;

    @TableField(exist = false)
    private List<AccountRole> roles;
    
    @TableField(exist = false)
    private User user;
    
    @TableField(exist = false)
    private Tenant tenant;
    
    @TableField(exist = false)
    private String token;

    public String getTypeName() {
        return EnumUtils.getName(AccountType.class, this.type);
    }

    public String getStateName() {
        return EnumUtils.getName(UserState.class, this.state);
    }
}
