package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.LostItemMapper;
import com.ofa.lostandfound.dto.PaginatedResponseDTO;
import com.ofa.lostandfound.dto.PaginatedResponseMapper;
import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.repository.LostItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class LostItemServiceImplIntegrationTest {

    @Autowired
    private LostItemServiceImpl lostItemService;

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private LostItemMapper lostItemMapper;

    @Autowired
    private PaginatedResponseMapper paginatedResponseMapper;

    @Autowired
    private PageableCreator pageableCreator;

    @Test
    public void givenLostItem_whenSave_thenReturnLostItemDTO() {
        // Given
        LostItem lostItem = new LostItem("Phone", 1, "Park");

        // When
        LostItemDTO result = lostItemService.save(lostItem);

        // Then
        assertNotNull(result);
        assertEquals("Phone", result.name());
        assertEquals(1, result.quantity());
        assertEquals("Park", result.place());
    }

    @Test
    public void givenLostItems_whenSaveAll_thenReturnListOfLostItemDTOs() {
        // Given
        LostItem lostItem1 = new LostItem("Phone", 1, "Park");
        LostItem lostItem2 = new LostItem("Wallet", 1, "Cafe");
        List<LostItem> lostItems = List.of(lostItem1, lostItem2);

        // When
        List<LostItemDTO> result = lostItemService.saveAll(lostItems);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Phone", result.get(0).name());
        assertEquals("Wallet", result.get(1).name());
    }

    @Test
    public void givenPageAndSize_whenGetAllLostItems_thenReturnPaginatedResponse() {
        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        LostItem lostItem = new LostItem("Phone", 1, "Park");
        lostItemRepository.save(lostItem);

        // When
        PaginatedResponseDTO<LostItemDTO> result = lostItemService.getAllLostItems(page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals("Phone", result.getItems().get(0).name());
    }
}
