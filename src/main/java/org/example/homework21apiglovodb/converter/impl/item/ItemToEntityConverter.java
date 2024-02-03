package org.example.homework21apiglovodb.converter.impl.item;

import org.example.homework21apiglovodb.converter.Converter;
import org.example.homework21apiglovodb.dto.ItemDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemToEntityConverter implements Converter<ItemDto, ItemEntity> {
    @Override
    public ItemEntity convert(ItemDto source) {
        if (source == null) {
            return null;
        }
        return ItemEntity.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }
}