package com.ofa.lostandfound.entity;

import com.ofa.lostandfound.repository.ClaimedItemRepository;
import com.ofa.lostandfound.repository.LostItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class ClaimedItemTest {

    @Autowired
    private ClaimedItemRepository claimedItemRepository;

    @Autowired
    private LostItemRepository lostItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void whenSavingClaimedItemsWithDifferentUsers_thenAllShouldBeRetrievable() {
        // Given (Setup - creating necessary data)
        LostItem lostItem = new LostItem();
        lostItem.setName("Lost Phone");
        lostItem.setQuantity(1);
        lostItem.setPlace("Library");
        lostItemRepository.save(lostItem);

        ClaimedItem claimedItem1 = new ClaimedItem();
        claimedItem1.setLostItem(lostItem);
        claimedItem1.setUserId("user123");
        claimedItem1.setQuantity(1);
        claimedItemRepository.save(claimedItem1);

        ClaimedItem claimedItem2 = new ClaimedItem();
        claimedItem2.setLostItem(lostItem);
        claimedItem2.setUserId("user456");
        claimedItem2.setQuantity(1);
        claimedItemRepository.save(claimedItem2);

        // When (Action - saving data and retrieving it)
        entityManager.flush();
        entityManager.clear();

        ClaimedItem foundItem1 = claimedItemRepository.findById(claimedItem1.getId()).orElse(null);
        ClaimedItem foundItem2 = claimedItemRepository.findById(claimedItem2.getId()).orElse(null);

        // Then (Assertions - verifying the results)
        assertNotNull(foundItem1, "ClaimedItem with userId 'user123' should be found in the database");
        assertEquals("user123", foundItem1.getUserId(), "UserId should be user123");
        assertEquals(1, foundItem1.getQuantity(), "Quantity should be 1");
        assertNotNull(foundItem1.getLostItem(), "LostItem should be associated");
        assertEquals("Lost Phone", foundItem1.getLostItem().getName(), "LostItem name should be 'Lost Phone'");

        assertNotNull(foundItem2, "ClaimedItem with userId 'user456' should be found in the database");
        assertEquals("user456", foundItem2.getUserId(), "UserId should be user456");
        assertEquals(1, foundItem2.getQuantity(), "Quantity should be 1");
        assertNotNull(foundItem2.getLostItem(), "LostItem should be associated");
        assertEquals("Lost Phone", foundItem2.getLostItem().getName(), "LostItem name should be 'Lost Phone'");
    }
}