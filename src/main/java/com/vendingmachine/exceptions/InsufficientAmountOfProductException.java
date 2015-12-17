package com.vendingmachine.exceptions;

public class InsufficientAmountOfProductException extends Exception {
    public InsufficientAmountOfProductException() {
        super("Product is not available, calling to supplier service");
    }
}
