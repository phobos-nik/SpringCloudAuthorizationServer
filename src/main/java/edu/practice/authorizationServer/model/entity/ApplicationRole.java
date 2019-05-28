package edu.practice.authorizationServer.model.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static edu.practice.authorizationServer.model.entity.ApplicationGrantedAuthority.*;

public enum ApplicationRole {

    ADMINISTRATOR(GET_USERS),
    USER(CREATE_NOTE);

    private Collection<GrantedAuthority> grantedAuthorities;

    ApplicationRole(GrantedAuthority... grantedAuthorities) {
        this.grantedAuthorities = new ArrayList<>();
        this.grantedAuthorities.addAll(Arrays.asList(grantedAuthorities));
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }
}
