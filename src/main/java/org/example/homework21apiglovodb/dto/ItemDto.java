package org.example.homework21apiglovodb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemDto {
    private long id;
    private String name;
    private double price;
}
