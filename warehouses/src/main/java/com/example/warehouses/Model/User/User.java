package com.example.warehouses.Model.User;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
public class User implements UserDetails {
    String ROLE_PREFIX = "ROLE_";

    private final String username;
    private final String password;
    private String role;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNotExpired;
    private final boolean accountNotLocked;
    private final boolean credentialsNotExpired;
    private final boolean enabled;


    public User(String username, String password, String role, Set<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.authorities = Collections.unmodifiableSet(authorities);
        this.accountNotExpired = true;
        this.accountNotLocked = true;
        this.credentialsNotExpired = true;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_PREFIX+role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
