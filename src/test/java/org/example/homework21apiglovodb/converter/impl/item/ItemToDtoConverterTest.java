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
@SpringBootTest(classes = ItemToDtoConverter.class)
class ItemToDtoConverterTest {

    @Autowired
    public ItemToDtoConverter converter;

    @Test
    void convertItemToDtoTest_ShouldReturnItemDto() {
        ItemEntity input = ItemEntity.builder().id(1L).name("item1").build();

        ItemDto result = converter.convert(input);

        assertEquals(input.getId(), result.getId());
        assertEquals(input.getName(), result.getName());
    }

    @Test
    void convertItemToDtoTest_ShouldReturnNull_WhenSourceIsNull() {
        ItemDto result = converter.convert((ItemEntity) null);

        assertNull(result);
    }
}