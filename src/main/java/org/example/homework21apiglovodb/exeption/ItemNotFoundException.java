package org.example.homework21apiglovodb.exeption;

public class ItemNotFoundException extends Exception {

    public static final String MESSAGE = "Item not found";
    public ItemNotFoundException() {

        super(MESSAGE);
    }
}