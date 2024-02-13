package org.example.homework21apiglovodb.exeption;

public class OrderNotFoundException extends Exception {

    public static final String MESSAGE = "Order not found";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}