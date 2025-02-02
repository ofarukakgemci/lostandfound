package com.ofa.lostandfound.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {


    private static final String OAUTH_SCHEME = "auth";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String authURL;


    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME));
    }

    private SecurityScheme createOAuthScheme() {
        Scopes scopes = new Scopes();
        scopes.addString("openid", "");
        final var oauthFlow = new OAuthFlow()
                .authorizationUrl(authURL + "/authorize")
                .refreshUrl(authURL + "/token")
                .tokenUrl(authURL + "/token")
                .scopes(scopes);
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(new OAuthFlows().authorizationCode(oauthFlow));
    }


    //    private static final String OPEN_ID_SCHEME_NAME = "openId";
//
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("API Title").description("API Description").version("1.0.0"))
//                .components(new Components()
//                        .addSecuritySchemes(OPEN_ID_SCHEME_NAME, new SecurityScheme()
//                                .type(SecurityScheme.Type.OPENIDCONNECT)
//                                .openIdConnectUrl("http://localhost:8090/.well-known/openid-configuration")))
//                .addSecurityItem(new SecurityRequirement().addList(OPEN_ID_SCHEME_NAME))
//                ;
//    }
}
