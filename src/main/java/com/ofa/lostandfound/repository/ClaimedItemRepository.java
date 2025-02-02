package com.ofa.lostandfound.repository;

import com.ofa.lostandfound.entity.ClaimedItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimedItemRepository extends JpaRepository<ClaimedItem, Long> {
    List<ClaimedItem> findByLostItemId(Long lostItemId);

    Page<ClaimedItem> findByUserId(String userId, Pageable pageable);
}