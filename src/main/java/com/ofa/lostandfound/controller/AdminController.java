package com.ofa.lostandfound.controller;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.entity.ClaimedItem;
import com.ofa.lostandfound.exception.DocumentNotProcessedException;
import com.ofa.lostandfound.service.ClaimedItemService;
import com.ofa.lostandfound.service.DocumentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private final ClaimedItemService claimedItemService;
    private final DocumentProcessingService documentProcessingService;

    @PostMapping(value = "/upload-lost-items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadLostItems(@RequestPart("file") MultipartFile file,
                                                               @RequestParam("fileType") String fileType) throws DocumentNotProcessedException {
        List<LostItemDTO> items = documentProcessingService.process(file, fileType);
        return ResponseEntity.ok(Map.of(
                "message", "File processed and lost items stored successfully."
                , "items", items));

    }


    @GetMapping("/claim")
    public PaginatedResponseDTO<ClaimedItem> getAllClaims(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        return claimedItemService.getAllClaims(page, size);
    }
}