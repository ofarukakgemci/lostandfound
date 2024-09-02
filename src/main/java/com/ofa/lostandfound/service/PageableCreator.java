package com.ofa.lostandfound.service;

import org.springframework.data.domain.Pageable;

public interface PageableCreator {

    Pageable createWithDefaultSort(int page, int size);
}