package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.LostItemMapper;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.dto.PaginatedResponseMapper;
import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.repository.LostItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostItemServiceImpl implements LostItemService {

    private final LostItemRepository lostItemRepository;
    private final PageableCreator pageableCreator;

    private final LostItemMapper lostItemMapper;
    private final PaginatedResponseMapper paginatedResponseMapper;

    @CacheEvict(value = "lostItemsCache", allEntries = true)
    public LostItemDTO save(LostItem lostItem) {
        return lostItemMapper.toDto(lostItemRepository.save(lostItem));
    }

    @CacheEvict(value = "lostItemsCache", allEntries = true)
    public List<LostItemDTO> saveAll(List<LostItem> lostItems) {
        return lostItemRepository.saveAll(lostItems).stream().map(lostItemMapper::toDto).collect(Collectors.toList());
    }

    @Cacheable(value = "lostItemsCache", key = "#page + '-' + #size", unless = "#result.items.size()==0")
    public PaginatedResponseDTO<LostItemDTO> getAllLostItems(int page, int size) {
        Page<LostItemDTO> result = lostItemRepository.findAll(pageableCreator.createWithDefaultSort(page, size)).map(lostItemMapper::toDto);
        return paginatedResponseMapper.toPaginatedResponse(result);
    }

}