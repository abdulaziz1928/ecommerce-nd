package com.example.demo.infrastructure.common.exceptions;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException(long itemId) {
        super(String.format("item with id (%d) not found", itemId));
    }
}
