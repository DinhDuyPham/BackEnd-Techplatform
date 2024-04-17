package com.learn.techplatform.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.techplatform.common.enums.UserRole;
import com.learn.techplatform.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthUser implements UserDetails {

    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private final boolean enabled;

    public AuthUser(String id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.userRole = role;
        this.enabled = true;
    }

    public AuthUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
        this.enabled = true;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
