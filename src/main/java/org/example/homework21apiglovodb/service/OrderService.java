package org.example.homework21apiglovodb.service;

import lombok.AllArgsConstructor;
import org.example.homework21apiglovodb.converter.Converter;
import org.example.homework21apiglovodb.dto.OrderDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.example.homework21apiglovodb.entity.OrderEntity;
import org.example.homework21apiglovodb.exeption.ItemNotFoundException;
import org.example.homework21apiglovodb.exeption.OrderNotFoundException;
import org.example.homework21apiglovodb.repository.ItemRepository;
import org.example.homework21apiglovodb.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final Converter<OrderEntity, OrderDto> toDto;
    private final Converter<OrderDto, OrderEntity> toEntity;

    public OrderDto getOrderById(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .map(toDto::convert)
                .orElseThrow(OrderNotFoundException::new);
    }

    public OrderDto saveOrder(OrderDto dto) {
        OrderEntity entity = orderRepository.save(toEntity.convert(dto));
        return toDto.convert(entity);
    }

    public OrderDto updateOrder(OrderDto dto) throws OrderNotFoundException {
        if (dto == null) {
            return null;
        }
        orderRepository.findById(dto.getId())
                .orElseThrow(OrderNotFoundException::new);
        OrderEntity updateEntity = orderRepository.save(toEntity.convert(dto));
        return toDto.convert(updateEntity);
    }

    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        orderRepository.deleteById(orderId);
    }

    public OrderDto addItemToOrder(Long orderId, Long itemId) throws OrderNotFoundException, ItemNotFoundException {
        if (itemId == null) {
            return null;
        }
        OrderEntity foundOrder = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        ItemEntity additionalItem = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);

        foundOrder.getItems().add(additionalItem);
        return toDto.convert(orderRepository.save(foundOrder));
    }

    public OrderDto deleteItemFromOrder(Long orderId, Long itemId) throws OrderNotFoundException, ItemNotFoundException {
        if (orderId == null) {
            return null;
        }
        if (itemId == null) {
            return null;
        }
        OrderEntity foundOrder = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        ItemEntity removeItem = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);

        List<ItemEntity> currentItems = foundOrder.getItems();
        currentItems.remove(removeItem);
        return toDto.convert(orderRepository.save(foundOrder));
    }
}