package org.example.homework21apiglovodb.converter.impl.order;

import org.example.homework21apiglovodb.converter.Converter;
import org.example.homework21apiglovodb.dto.ItemDto;
import org.example.homework21apiglovodb.dto.OrderDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.example.homework21apiglovodb.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderToDtoConverter.class)
@ExtendWith(MockitoExtension.class)
class OrderToDtoConverterTest {

    @Autowired
    public OrderToDtoConverter orderConverter;
    @MockBean
    public Converter<ItemEntity, ItemDto> itemsConverter;

    @Test
    void convertOrderToDtoTest_ShouldReturnOrderDto() {
        List<ItemEntity> itemEntities = List.of(
                ItemEntity.builder()
                        .id(1L)
                        .name("item")
                        .build()
        );
        OrderEntity inputEntity = OrderEntity.builder()
                .id(1L)
                .customerName("User")
                .items(itemEntities)
                .build();
        List<ItemDto> itemDtos = List.of(
                ItemDto.builder()
                        .id(1L)
                        .name("item")
                        .build()
        );
        when(itemsConverter.convert(anyList())).thenReturn(itemDtos);
        OrderDto result = orderConverter.convert(inputEntity);

        assertEquals(inputEntity.getId(), result.getId());
        assertEquals(inputEntity.getCustomerName(), result.getCustomerName());
        assertEquals(inputEntity.getItems().size(), result.getItems().size());
    }

    @Test
    void convertOrderToDtoTest_shouldReturnNullWhenSourceIsNull() {
        OrderDto result = orderConverter.convert((OrderEntity) null);
        assertNull(result);
    }
}