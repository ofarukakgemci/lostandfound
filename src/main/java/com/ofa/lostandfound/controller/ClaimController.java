package com.ofa.lostandfound.controller;

import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.entity.ClaimedItem;
import com.ofa.lostandfound.service.ClaimedItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/claim")
public class ClaimController {
    private final ClaimedItemService claimedItemService;

    @PostMapping
    public ResponseEntity<?> claimLostItem(Authentication authentication, @RequestParam Long lostItemId, @RequestParam int quantity) {
        claimedItemService.claimLostItem(lostItemId, authentication.getName(), quantity);
        return ResponseEntity.ok("Item claimed successfully");
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ClaimedItem>> getUserClaims(Authentication authentication,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(claimedItemService.getUserClaims(authentication.getName(), page, size));
    }
}
