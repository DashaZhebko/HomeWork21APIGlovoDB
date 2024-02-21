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
@SpringBootTest(classes = OrderToEntityConverter.class)
@ExtendWith(MockitoExtension.class)
class OrderToEntityConverterTest {

    @Autowired
    public OrderToEntityConverter orderConverter;

    @MockBean
    public Converter<ItemDto, ItemEntity> itemsConverter;

    @Test
    void convertOrderToEntityTest_ShouldReturnEntity() {
        List<ItemDto> inputDtoItems = List.of(
                ItemDto.builder()
                        .name("item")
                        .build()
        );
        List<ItemEntity> itemEntities = List.of(
                ItemEntity.builder()
                        .id(1L)
                        .name("item")
                        .build()
        );
        OrderDto inputDto = OrderDto.builder()
                .customerName("User")
                .items(inputDtoItems)
                .build();

        when(itemsConverter.convert(anyList())).thenReturn(itemEntities);
        OrderEntity result = orderConverter.convert(inputDto);

        assertEquals(inputDto.getId(), result.getId());
        assertEquals(inputDto.getCustomerName(), result.getCustomerName());
        assertEquals(inputDto.getItems().size(), result.getItems().size());
    }

    @Test
    void convertOrderToEntityTest_ShouldReturnNullWhenSourceIsNull() {
        OrderEntity result = orderConverter.convert((OrderDto) null);
        assertNull(result);
    }
}