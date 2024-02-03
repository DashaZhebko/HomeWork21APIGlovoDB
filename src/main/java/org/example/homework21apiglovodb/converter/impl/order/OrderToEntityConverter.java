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
public class OrderToEntityConverter implements Converter<OrderDto, OrderEntity> {
    private final Converter<ItemDto, ItemEntity> itemEntityConverter;

    @Override
    public OrderEntity convert(OrderDto source) {
        if (source == null) {
            return null;
        }
        return OrderEntity.builder()
                .id(source.getId())
                .customerName(source.getCustomerName())
                .items(itemEntityConverter.convert(source.getItems()))
                .build();
    }
}