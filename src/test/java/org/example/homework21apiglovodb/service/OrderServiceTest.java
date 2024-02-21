package org.example.homework21apiglovodb.service;

import org.example.homework21apiglovodb.converter.Converter;
import org.example.homework21apiglovodb.dto.ItemDto;
import org.example.homework21apiglovodb.dto.OrderDto;
import org.example.homework21apiglovodb.entity.ItemEntity;
import org.example.homework21apiglovodb.entity.OrderEntity;
import org.example.homework21apiglovodb.exeption.ItemNotFoundException;
import org.example.homework21apiglovodb.exeption.OrderNotFoundException;
import org.example.homework21apiglovodb.repository.ItemRepository;
import org.example.homework21apiglovodb.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderService.class)
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Autowired
    public OrderService orderService;

    @MockBean
    public OrderRepository orderRepository;

    @MockBean
    public ItemRepository itemRepository;

    @MockBean
    public Converter<OrderEntity, OrderDto> toDto;

    @MockBean
    public Converter<OrderDto, OrderEntity> toEntity;

    @Test
    void getOrderByIdTest_ShouldReturnOrderDto() throws OrderNotFoundException {
        Long id = 1L;
        OrderDto expectedDto = OrderDto.builder()
                .id(id)
                .customerName("User")
                .items(List.of())
                .build();
        OrderEntity entity = OrderEntity.builder()
                .customerName("User")
                .items(List.of())
                .build();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(toDto.convert(entity)).thenReturn(expectedDto);

        OrderDto result = orderService.getOrderById(id);

        assertEquals(expectedDto, result);
    }

    @Test
    void getOrderByIdTest_nonExistingOrderId_shouldThrowOrderNotFoundException() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(anyLong()));
    }

    @Test
    void saveOrderTest_shouldReturnOrderDto() {
        OrderDto inputDto = OrderDto.builder()
                .customerName("User")
                .items(List.of())
                .build();
        OrderEntity savedEntity = OrderEntity.builder()
                .id(1L)
                .customerName("User")
                .items(List.of())
                .build();

        when(toEntity.convert(inputDto)).thenReturn(savedEntity);
        when(orderRepository.save(any())).thenReturn(savedEntity);
        when(toDto.convert(savedEntity)).thenReturn(inputDto);

        OrderDto result = orderService.saveOrder(inputDto);
        assertEquals(inputDto, result);
    }

    @Test
    void updateOrderTest_ShouldReturnOrderDto_WhenOrderIdIsPresent() throws OrderNotFoundException {
        OrderDto inputDto = OrderDto.builder()
                .id(1L)
                .customerName("User")
                .items(List.of())
                .build();

        OrderEntity savedEntity = OrderEntity.builder()
                .id(1L)
                .customerName("User")
                .items(List.of())
                .build();

        when(toEntity.convert(inputDto)).thenReturn(savedEntity);
        when(orderRepository.save(any())).thenReturn(savedEntity);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(savedEntity));
        when(toDto.convert(savedEntity)).thenReturn(inputDto);

        OrderDto result = orderService.updateOrder(inputDto);

        assertEquals(inputDto, result);
    }

    @Test
    void updateOrderTest_WhenOrderNotFound_ShouldTrowsOrderNotFoundException() {
        Long id = 1L;
        OrderDto inputDto = OrderDto.builder()
                .id(id)
                .build();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(inputDto));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteOrderTest_Success() throws OrderNotFoundException {
        Long id = 1L;
        OrderEntity input = OrderEntity.builder()
                .id(id)
                .build();
        when(orderRepository.findById(id)).thenReturn(Optional.of(input));

        orderService.deleteOrder(id);
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteOrderTest_WhenOrderNotFound_ShouldTrowsOrderNotFoundException() {
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(id));
        verify(orderRepository, never()).deleteById(id);
    }

    @Test
    void addItemToOrderTest_WhenOrderAndItemIsPresent_ShouldReturnOrderDto() throws OrderNotFoundException, ItemNotFoundException {
        Long orderId = 1L;
        Long itemId = 1L;
        List<ItemEntity> itemEntities = new ArrayList<>();
        List<ItemDto> itemDtos = new ArrayList<>();

        ItemEntity savedItem = ItemEntity.builder()
                .id(itemId)
                .build();
        itemEntities.add(savedItem);

        OrderEntity savedOrderEntity = OrderEntity.builder()
                .id(orderId)
                .items(itemEntities)
                .build();
        ItemDto itemDto = ItemDto.builder()
                .id(itemId)
                .build();
        itemDtos.add(itemDto);

        OrderDto expectedDto = OrderDto.builder()
                .id(orderId)
                .items(itemDtos)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(savedOrderEntity));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(savedItem));
        when(orderRepository.save(savedOrderEntity)).thenReturn(savedOrderEntity);
        when(toDto.convert(savedOrderEntity)).thenReturn(expectedDto);

        OrderDto result = orderService.addItemToOrder(orderId, itemId);

        assertEquals(expectedDto, result);
    }

    @Test
    void addItemToOrderTest_WhenOrderNotFound_ShouldTrowsOrderNotFoundException() {
        Long orderId = 1L;
        Long itemId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.addItemToOrder(orderId, itemId));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void addItemToOrderTest_WhenOrderItemNotFound_ShouldTrowsItemNotFoundException() {
        Long orderId = 1L;
        Long itemId = 1L;
        OrderEntity foundEntity = OrderEntity.builder()
                .id(orderId)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(foundEntity));
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> orderService.addItemToOrder(orderId, itemId));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteItemFromOrderTest_Success() throws OrderNotFoundException, ItemNotFoundException {
        Long orderId = 1L;
        Long itemId = 1L;
        List<ItemEntity> itemEntities = new ArrayList<>();
        List<ItemDto> itemDtos = new ArrayList<>();

        ItemEntity savedItem = ItemEntity.builder()
                .id(itemId)
                .build();

        OrderEntity savedOrderEntity = OrderEntity.builder()
                .id(orderId)
                .items(itemEntities)
                .build();

        OrderDto expectedDto = OrderDto.builder()
                .id(orderId)
                .items(itemDtos)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(savedOrderEntity));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(savedItem));
        when(orderRepository.save(savedOrderEntity)).thenReturn(savedOrderEntity);
        when(toDto.convert(savedOrderEntity)).thenReturn(expectedDto);

        OrderDto result = orderService.deleteItemFromOrder(orderId, itemId);

        assertEquals(expectedDto, result);
    }

    @Test
    void deleteItemFromOrderTest_WhenOrderNotFound_ShouldTrowOrderNotFoundException() {
        Long orderId = 1L;
        Long itemId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteItemFromOrder(orderId, itemId));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteItemFromOrderTest_WhenItemNotFound_ShouldTrowItemNotFoundException() {
        Long orderId = 1L;
        Long itemId = 1L;

        OrderEntity foundEntity = OrderEntity.builder()
                .id(orderId)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(foundEntity));
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> orderService.addItemToOrder(orderId, itemId));
        verify(orderRepository, never()).save(any());
    }
}