package com.ofa.lostandfound.dto;


import com.ofa.lostandfound.entity.LostItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LostItemMapper {
    LostItemMapper INSTANCE = Mappers.getMapper(LostItemMapper.class);

    LostItemDTO toDto(LostItem entity);

    LostItem toEntity(LostItemDTO dto);
}
