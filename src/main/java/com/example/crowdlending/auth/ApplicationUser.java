package com.example.crowdlending.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.crowdlending.repository.model.User;

import java.util.Collections;
import java.util.Set;

public class ApplicationUser implements UserDetails {

    private final String username;
    private final String password;
    private final Set<? extends GrantedAuthority> grantedAuthorities;

    public ApplicationUser(String username,
                           String password,
                           Set<? extends GrantedAuthority> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.emptySet());
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
