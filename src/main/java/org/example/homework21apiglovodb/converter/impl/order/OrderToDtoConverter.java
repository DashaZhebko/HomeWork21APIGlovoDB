package org.example.homework21apiglovodb.converter.impl.order;

import lombok.AllArgsConstructor;
import org.example.homework21apiglovodb.converter.Converter;
import org.example.homework21apiglovodb.dto.ItemDto;
import org.example.homework21apiglovodb.dto.OrderDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.example.homework21apiglovodb.entity.OrderEntity;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderToDtoConverter implements Converter<OrderEntity, OrderDto> {
    private final Converter<ItemEntity, ItemDto> itemDtoConverter;

    @Override
    public OrderDto convert(OrderEntity source) {
        if (source == null) {
            return null;
        }
        return OrderDto.builder()
                .id(source.getId())
                .customerName(source.getCustomerName())
                .items(itemDtoConverter.convert(source.getItems()))
                .build();
    }
}