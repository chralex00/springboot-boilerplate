package com.zeniapp.segmentmiddleware.utils;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthentication extends AbstractAuthenticationToken {
        private final String jwt;
    
        public JwtAuthentication(String jwt, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.jwt = jwt;
            setAuthenticated(true);
        }
    
        @Override
        public Object getCredentials() {
            return null;
        }
    
        @Override
        public Object getPrincipal() {
            return this.jwt;
        }
    }