package com.ofa.lostandfound.controller;

import com.ofa.lostandfound.config.SecurityConfig;
import com.ofa.lostandfound.dto.PageMetadataDTO;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.entity.ClaimedItem;
import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.service.ClaimedItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(ClaimController.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ClaimControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaimedItemService claimedItemService;

    SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtUser = jwt();


    @Test
    public void givenValidRequest_whenClaimLostItem_thenReturnSuccessMessage() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(post("/claim")
                        .param("lostItemId", "1")
                        .param("quantity", "1")
                        .with(jwtUser))
                .andExpect(status().isOk())
                .andExpect(content().string("Item claimed successfully"));
    }

    @Test
    public void givenInvalidQuantity_whenClaimLostItem_thenReturnBadRequest() throws Exception {
        // Given
        Mockito.doThrow(new IllegalArgumentException("Invalid quantity"))
                .when(claimedItemService).claimLostItem(anyLong(), anyString(), anyInt());

        // When & Then
        mockMvc.perform(post("/claim")
                        .param("lostItemId", "1")
                        .param("quantity", "-1")
                        .with(jwtUser))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid quantity"));
    }

    @Test
    public void givenNoLostItemId_whenClaimLostItem_thenReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/claim")
                        .param("quantity", "1")
                        .with(jwtUser))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Required parameter 'lostItemId' is not present."));
    }

    @Test
    public void givenValidRequest_whenGetUserClaims_thenReturnPaginatedClaims() throws Exception {
        // Given
        LostItem lostItem = new LostItem();
        lostItem.setName("Phone");

        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setId(1L);
        claimedItem.setLostItem(lostItem);
        claimedItem.setQuantity(1);

        PaginatedResponseDTO<ClaimedItem> responseDTO = new PaginatedResponseDTO<>(new PageMetadataDTO(), List.of(claimedItem));
        Mockito.when(claimedItemService.getUserClaims(anyString(), anyInt(), anyInt()))
                .thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/claim")
                        .param("page", "0")
                        .param("size", "10")
                        .with(jwtUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].lostItem.name", is("Phone")))
                .andExpect(jsonPath("$.items[0].quantity", is(1)));
    }

    @Test
    public void givenNoAuthentication_whenClaimLostItem_thenReturnUnauthorized() throws Exception {
        // When & Then
        mockMvc.perform(post("/claim")
                        .param("lostItemId", "1")
                        .param("quantity", "1"))
                .andExpect(status().isUnauthorized());
    }

}
