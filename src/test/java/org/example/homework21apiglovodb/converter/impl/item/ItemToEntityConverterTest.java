package org.example.homework21apiglovodb.converter.impl.item;

import org.example.homework21apiglovodb.dto.ItemDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemToEntityConverter.class)
class ItemToEntityConverterTest {
    @Autowired
    public ItemToEntityConverter converter;

    @Test
    void convertItemToDtoTest_ShouldReturnItemEntity() {
        ItemDto inputDto = ItemDto.builder()
                .id(1L)
                .name("Item")
                .price(2.2)
                .build();

        ItemEntity result = converter.convert(inputDto);
        assertEquals(inputDto.getId(), result.getId());
        assertEquals(inputDto.getName(), result.getName());
        assertEquals(inputDto.getPrice(), result.getPrice());
    }

    @Test
    void convertItemToDtoTest_ShouldReturnNullIfSourceIsNull() {
        ItemEntity result = converter.convert((ItemDto) null);
        assertNull(result);
    }
}