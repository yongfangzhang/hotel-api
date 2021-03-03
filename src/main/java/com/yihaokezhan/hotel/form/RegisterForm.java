package com.yihaokezhan.hotel.form;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.yihaokezhan.hotel.module.entity.Account;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "账户不能为空")
    private Account account;

    private User user;

    private Tenant tenant;

    private Integer roleType;

}
