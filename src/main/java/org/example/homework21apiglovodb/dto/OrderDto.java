package org.example.homework21apiglovodb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class OrderDto {
    private long id;
    private String customerName;
    private List<ItemDto> items;
}
