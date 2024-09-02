package com.ofa.lostandfound.controller;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.service.LostItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lost-item")
public class LostItemController {
    private final LostItemService lostItemService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<LostItemDTO>> getLostItems(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(lostItemService.getAllLostItems(page, size));
    }

}