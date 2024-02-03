package org.example.homework21apiglovodb.converter.impl.item;

import org.example.homework21apiglovodb.converter.Converter;
import org.example.homework21apiglovodb.dto.ItemDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemToDtoConverter implements Converter<ItemEntity, ItemDto> {
    @Override
    public ItemDto convert(ItemEntity source) {
        if (source == null) {
            return null;
        }
        return ItemDto.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }
}