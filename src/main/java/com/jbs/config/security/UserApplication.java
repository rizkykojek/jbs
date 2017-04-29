package com.jbs.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by rizkykojek on 4/29/17.
 */
@Getter
@Setter
public class UserApplication extends User {

    private Long employeeId;
    private String fullName;

    public UserApplication(String username, String password, Collection<? extends GrantedAuthority> authorities, Long employeeId, String fullName) {
        super(username, password, authorities);

        this.employeeId = employeeId;
        this.fullName = fullName;
    }
}
