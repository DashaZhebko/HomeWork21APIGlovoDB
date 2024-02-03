package org.example.homework21apiglovodb.controller;

import lombok.AllArgsConstructor;
import org.example.homework21apiglovodb.dto.OrderDto;
import org.example.homework21apiglovodb.exeption.ItemNotFoundException;
import org.example.homework21apiglovodb.exeption.OrderNotFoundException;
import org.example.homework21apiglovodb.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable long id) {
        try {
            return orderService.getOrderById(id);
        } catch (OrderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping
    public OrderDto save(@RequestBody OrderDto newOrder) {
        return orderService.saveOrder(newOrder);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        try {
            orderService.deleteOrder(id);
        } catch (OrderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping
    public OrderDto update(@RequestBody OrderDto order) {
        try {
            return orderService.updateOrder(order);
        } catch (OrderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PatchMapping("/{orderId}/items/{itemId}")
    public OrderDto add(@PathVariable long orderId, @PathVariable long itemId) {
        try {
            return orderService.addItemToOrder(orderId, itemId);
        } catch (OrderNotFoundException | ItemNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public OrderDto deleteItem(@PathVariable long orderId, @PathVariable long itemId) {
        try {
            return orderService.deleteItemFromOrder(orderId, itemId);
        } catch (OrderNotFoundException | ItemNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}