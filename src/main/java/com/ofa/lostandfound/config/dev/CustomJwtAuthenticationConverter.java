package com.ofa.lostandfound.config.dev;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) {
        String name = jwt.getClaim("sub");

        // With the mock OAuth2 server, any username starting with 'admin' is considered an admin.
        if (ObjectUtils.isNotEmpty(name) && name.startsWith("admin")) {
            return Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.emptySet();
    }

    public CustomJwtAuthenticationConverter() {

    }

    @Override
    public AbstractAuthenticationToken convert(final Jwt source) {
        Collection<GrantedAuthority> authorities = new HashSet<>(extractResourceRoles(source));
        return new JwtAuthenticationToken(source, authorities);
    }
}