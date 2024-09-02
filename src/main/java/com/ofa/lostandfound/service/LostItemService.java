package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.entity.LostItem;

import java.util.List;

public interface LostItemService {

    PaginatedResponseDTO<LostItemDTO> getAllLostItems(int page, int size);


    LostItemDTO save(LostItem lostItem);


    List<LostItemDTO> saveAll(List<LostItem> lostItems);
}