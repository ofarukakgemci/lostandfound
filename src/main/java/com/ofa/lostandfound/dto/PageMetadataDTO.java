package com.ofa.lostandfound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadataDTO {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}