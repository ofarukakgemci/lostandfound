package com.ofa.lostandfound.dto;

import java.util.Date;

public record ClaimedItemDTO(
        Long id,
        Date createdAt,
        LostItemDTO lostItem,
        String userId,
        Integer quantity) {
}
