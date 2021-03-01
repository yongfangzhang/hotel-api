package com.yihaokezhan.hotel.module.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 * 租户表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "tenant", autoResultMap = true)
public class Tenant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "tenant";

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空", groups = AddGroup.class)
    private String name;

    /**
     * 法人
     */
    @NotBlank(message = "法人不能为空", groups = AddGroup.class)
    private String legalPerson;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空", groups = AddGroup.class)
    private String contactor;

    /**
     * 联系人手机号
     */
    @NotBlank(message = "联系人手机号不能为空", groups = AddGroup.class)
    @Pattern(regexp = Constant.PATTERN_MOBILE, message = "手机号格式错误",
            groups = {AddGroup.class, UpdateGroup.class})
    private String contactorMobile;

    /**
     * 是否已删除
     */
    private Boolean deleted;
}
