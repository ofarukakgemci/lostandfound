package com.ofa.lostandfound.dto;

import java.util.Date;

public record LostItemDTO(
        Long id,
        Date createdAt,
        String name,
        Integer quantity,
        String place) {
}
