package edu.practice.authorizationServer.model.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString(of = {"username", "enabled"})
@EqualsAndHashCode(of = {"username", "password"})
public class ApplicationUserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Getter
    private Collection<? extends GrantedAuthority> authorities;

    @Getter
    private String password;

    @Getter
    private String username;

    @Getter
    private boolean enabled;

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }
}
