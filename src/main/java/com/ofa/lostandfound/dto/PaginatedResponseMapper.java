package com.ofa.lostandfound.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PaginatedResponseMapper {

    PaginatedResponseMapper INSTANCE = Mappers.getMapper(PaginatedResponseMapper.class);

    default <T> PaginatedResponseDTO<T> toPaginatedResponse(Page<T> page) {
        PageMetadataDTO pageMetadata = mapPageMetadata(page);
        return new PaginatedResponseDTO<>(pageMetadata, page.getContent());
    }

    default PageMetadataDTO mapPageMetadata(Page<?> page) {
        return new PageMetadataDTO(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}