package edu.practice.authorizationServer.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum ApplicationGrantedAuthority implements GrantedAuthority {

    GET_USERS("GET_USERS"),
    CREATE_NOTE("CREATE_NOTE");

    private String grantedAuthority;

    ApplicationGrantedAuthority(String grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    @Override
    public String getAuthority() {
        return grantedAuthority;
    }
}
