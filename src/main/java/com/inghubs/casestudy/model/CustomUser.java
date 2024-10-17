package com.inghubs.casestudy.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private Long customerId;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long customerId) {
        super(username, password, authorities);
        this.customerId = customerId;
    }
}
