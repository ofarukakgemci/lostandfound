package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.dto.PaginatedResponseMapper;
import com.ofa.lostandfound.entity.ClaimedItem;
import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.repository.ClaimedItemRepository;
import com.ofa.lostandfound.repository.LostItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimedItemServiceImpl implements ClaimedItemService {
    private final ClaimedItemRepository claimedItemRepository;
    private final LostItemRepository lostItemRepository;
    private final PageableCreator pageableCreator;
    private final PaginatedResponseMapper paginatedResponseMapper;

    public ClaimedItem claimItem(ClaimedItem claimedItem) {
        return claimedItemRepository.save(claimedItem);
    }

    public ClaimedItem claimLostItem(Long lostItemId, String userId, int claimedQuantity) {
        LostItem lostItem = lostItemRepository.findById(lostItemId).orElseThrow();
        ClaimedItem claim = new ClaimedItem(lostItem, userId, claimedQuantity);
        return claimedItemRepository.save(claim);
    }

    public PaginatedResponseDTO<ClaimedItem> getAllClaims(int page, int size) {
        Page<ClaimedItem> result = claimedItemRepository.findAll(pageableCreator.createWithDefaultSort(page, size));
        return paginatedResponseMapper.toPaginatedResponse(result);
    }

    public PaginatedResponseDTO<ClaimedItem> getUserClaims(String userId, int page, int size) {
        Page<ClaimedItem> result = claimedItemRepository.findByUserId(userId, pageableCreator.createWithDefaultSort(page, size));
        return paginatedResponseMapper.toPaginatedResponse(result);
    }
}
