package com.yihaokezhan.hotel.common.shiro;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiroSet implements Serializable {
    private static final long serialVersionUID = 1L;

    private Set<String> roles = new HashSet<String>();;

    private Set<String> permissions = new HashSet<String>();;

    public ShiroSet() {
    }

    public ShiroSet(Set<String> rs, Set<String> ps) {
        this.roles = rs;
        this.permissions = ps;
    }
}
