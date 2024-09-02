package com.ofa.lostandfound.config.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@Profile("dev")
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/explorer/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                //Security headers can be configured by the security team at an external layer, such as a gateway, web firewall, or similar
                .headers((headers) -> headers
                        .contentSecurityPolicy((csp) ->
                                csp.policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self';")
                                        .reportOnly())
                        .referrerPolicy(referrerPolicyConfig
                                -> referrerPolicyConfig.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))

                )
                //Stateless sessions with JWT from a third-party resource server ensures easy scalability
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer.jwt((jwt) ->
                                // With the mock OAuth2 server, any username starting with 'admin' is considered an admin.
                                jwt.jwtAuthenticationConverter(new CustomJwtAuthenticationConverter()))
                )
        ;

        return http.build();
    }

}