package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.entity.ClaimedItem;

public interface ClaimedItemService {

    ClaimedItem claimItem(ClaimedItem claimedItem);

    ClaimedItem claimLostItem(Long lostItemId, String userId, int claimedQuantity);

    PaginatedResponseDTO<ClaimedItem> getAllClaims(int page, int size);

    PaginatedResponseDTO<ClaimedItem> getUserClaims(String userId, int page, int size);
}