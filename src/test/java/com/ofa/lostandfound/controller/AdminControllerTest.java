package com.ofa.lostandfound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofa.lostandfound.config.SecurityConfig;
import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.entity.ClaimedItem;
import com.ofa.lostandfound.service.ClaimedItemService;
import com.ofa.lostandfound.service.DocumentProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(AdminController.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClaimedItemService claimedItemService;

    @MockBean
    private DocumentProcessingService documentProcessingService;

    @BeforeEach
    void setUp() {

    }

    SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtAdmin = jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
    SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtUser = jwt();

    @Test
    void givenValidFile_whenUploadLostItems_thenReturnSuccess() throws Exception {
        // Given
        String fileContent = "ItemName: Example\nQuantity: 10\nPlace: Somewhere";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());
        String fileType = "text";

        LostItemDTO lostItemDTO = new LostItemDTO(1L, new java.util.Date(), "Example", 10, "Somewhere");
        List<LostItemDTO> items = List.of(lostItemDTO);

        when(documentProcessingService.process(file, fileType)).thenReturn(items);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/upload-lost-items")
                        .file(file)
                        .with(jwtAdmin)
                        .param("fileType", fileType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("File processed and lost items stored successfully."))
                .andExpect(jsonPath("$.items[0].name").value("Example"))
                .andExpect(jsonPath("$.items[0].quantity").value(10))
                .andExpect(jsonPath("$.items[0].place").value("Somewhere"));
    }

    @Test
    void givenNonAdminUser_whenAccessUploadLostItems_thenReturnForbidden() throws Exception {
        // Given
        String fileContent = "ItemName: Example\nQuantity: 10\nPlace: Somewhere";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());
        String fileType = "text";

        when(documentProcessingService.process(file, fileType)).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/upload-lost-items")
                        .file(file)
                        .with(jwtUser)
                        .param("fileType", fileType))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenValidRequest_whenGetAllClaims_thenReturnPaginatedClaims() throws Exception {
        // Given
        PaginatedResponseDTO<ClaimedItem> paginatedResponse = new PaginatedResponseDTO<>();
        when(claimedItemService.getAllClaims(0, 20)).thenReturn(paginatedResponse);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/claim")
                        .param("page", "0")
                        .param("size", "20")
                        .with(jwtAdmin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(paginatedResponse)));
    }

    @Test
    public void whenUploadLostItemsWithUnsupportedFileType_thenReturnError() throws Exception {
        // Given
        when(documentProcessingService.process(any(), anyString())).thenThrow(new IllegalArgumentException("Unsupported File Type"));

        // When & Then
        mockMvc.perform(multipart("/admin/upload-lost-items")
                        .file("file", "dummy file content".getBytes()) // Simulate file upload
                        .with(jwtAdmin)
                        .param("fileType", "unsupported"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unsupported File Type"));

    }

    @Test
    void givenNonAdminUser_whenGetAllClaims_thenReturnForbidden() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/claim")
                        .param("page", "0")
                        .param("size", "20")
                        .with(jwtUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


}
