package com.ofa.lostandfound.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LostItemDTOTest {

    @Test
    public void testLostItemDTOCreation() {
        // given
        Long id = 1L;
        Date createdAt = new Date();
        String name = "Lost Item";
        Integer quantity = 10;
        String place = "Library";

        // when
        LostItemDTO lostItemDTO = new LostItemDTO(id, createdAt, name, quantity, place);

        // then
        assertNotNull(lostItemDTO, "LostItemDTO must not be null");
        assertEquals(id, lostItemDTO.id());
        assertEquals(createdAt, lostItemDTO.createdAt());
        assertEquals(name, lostItemDTO.name());
        assertEquals(quantity, lostItemDTO.quantity());
        assertEquals(place, lostItemDTO.place());

    }

    @Test
    public void testLostItemDTOEquality() {
        // given
        Long id = 1L;
        Date createdAt = new Date();
        String name = "Lost Item";
        Integer quantity = 10;
        String place = "Library";

        // when
        LostItemDTO lostItemDTO1 = new LostItemDTO(id, createdAt, name, quantity, place);
        LostItemDTO lostItemDTO2 = new LostItemDTO(id, createdAt, name, quantity, place);

        // then
        assertEquals(lostItemDTO1, lostItemDTO2);
    }
}
