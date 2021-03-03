package com.yihaokezhan.hotel.form;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.yihaokezhan.hotel.common.utils.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号名
     */
    @NotBlank(message = "账号名不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = Constant.PATTERN_PASSWORD, message = Constant.PATTERN_PASSWORD_MSG)
    private String password;

    /**
     * 图片验证码SID
     */
    @NotBlank(message = "图片验证码SID不能为空")
    private String captchaSid;

    /**
     * 图片验证码
     */
    @NotBlank(message = "图片验证码不能为空")
    private String captchaCode;

    /**
     * 30天内自动登录
     */
    private boolean autoLogin = true;

    /**
     * 账号类型
     */
    private Integer type;

    /**
     * 设备
     */
    private String device;

    /**
     * UA
     */
    private String userAgent;
}
