package com.yihaokezhan.hotel.module.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.Gender;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 * 微信绑定的用户信息 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "wx_user_info", autoResultMap = true)
public class WxUserInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "wx_user_info";

    /**
     * 微信OpenId
     */
    private String openId;

    /**
     * 微信UUID
     */
    private String unionId;

    /**
     * 微信头像URL
     */
    private String avatarUrl;

    /**
     * 微信昵称
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

    public String getGenderName() {
        return EnumUtils.getName(Gender.class, this.gender);
    }

}
