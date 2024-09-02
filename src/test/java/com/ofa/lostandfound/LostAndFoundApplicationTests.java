package com.ofa.lostandfound;

import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.repository.LostItemRepository;
import com.ofa.lostandfound.service.LostItemServiceImpl;
import com.ofa.lostandfound.dto.LostItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class LostAndFoundApplicationTests {
	@MockBean
	private LostItemRepository lostItemRepository;

	@Autowired
	private LostItemServiceImpl lostItemService;

	@Test
	public void testSaveLostItem() {
		LostItem item = new LostItem("Laptop", 1, "Taxi");


		when(lostItemRepository.save(any(LostItem.class))).thenReturn(item);
		LostItemDTO savedItem = lostItemService.save(item);
		assertEquals("Laptop", savedItem.name());
	}
	@Test
	void contextLoads() {
	}

}
