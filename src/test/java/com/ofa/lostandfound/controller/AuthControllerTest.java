package com.ofa.lostandfound.controller;

import com.ofa.lostandfound.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetCurrentUser() throws Exception {
        // Mock JWT token
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "12345")
                .claim("scope", "ROLE_USER")
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, List.of());

        // Mock UserService
        when(userService.getUserName("12345")).thenReturn("testuser");

        // Perform GET /auth/me
        ResultActions result = mockMvc.perform(get("/auth/me")
                        .with(jwt().jwt(jwt))) // Adds the JWT token to the request

                // Expect HTTP 200 OK
                .andExpect(status().isOk())

                // Expect the correct JSON response structure
                .andExpect(jsonPath("$.userId").value("12345"))
                .andExpect(jsonPath("$.userName").value("testuser"))
                .andExpect(jsonPath("$.authorities").exists())
                .andExpect(jsonPath("$.exp").exists());

        // Verify that the UserService was called correctly
        verify(userService).getUserName("12345");
    }
}
