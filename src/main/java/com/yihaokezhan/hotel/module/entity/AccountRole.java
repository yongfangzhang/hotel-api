package com.yihaokezhan.hotel.module.entity;

import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.AccountType;
import com.yihaokezhan.hotel.common.enums.RoleType;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 * 用户角色表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "account_role", autoResultMap = true)
public class AccountRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "account_role";

    /**
     * 账户UUID
     */
    @NotBlank(message = "账户不能为空", groups = AddGroup.class)
    private String accountUuid;

    /**
     * 角色UUID
     */
    @NotBlank(message = "角色不能为空", groups = AddGroup.class)
    private String roleUuid;

    @TableField(exist = false)
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String tenantUuid, Integer accountType, Integer roleType) {
        RoleType rt = EnumUtils.valueOf(RoleType.class, roleType);
        if (RoleType.UNKNOWN.equals(rt)) {
            AccountType at = EnumUtils.valueOf(AccountType.class, accountType);
            rt = at.getRoleType();
        }
        if (RoleType.UNKNOWN.equals(rt)) {
            this.role = null;
            return;
        }
        Role role = new Role();
        role.setTenantUuid(tenantUuid);
        role.setAccountType(accountType);
        role.setCode(rt.getCode());
        this.role = role;
    }
}
