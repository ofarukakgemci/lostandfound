package com.ofa.lostandfound.entity;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.dto.LostItemMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class LostItemMapperTest {

    @Test
    public void shouldMapLostItemToDto() {
        //given
        LostItem item = new LostItem("Laptop", 1, "Taxi");

        //when
        LostItemDTO itemDTO = LostItemMapper.INSTANCE.toDto(item);

        //then
        Assertions.assertNotNull(itemDTO);
        assertThat(itemDTO.name(),is(equalTo("Laptop")) );
        assertThat(itemDTO.quantity(),is(equalTo(1)));
        assertThat(itemDTO.place(),is(equalTo("Taxi")));
    }
}
