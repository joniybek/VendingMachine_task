package com.vendingmachine.exceptions;

public class InsufficientAmountOfMoneyException extends Exception {
    public InsufficientAmountOfMoneyException() {
        super("Money inserted is not enough for buying the product");
    }

    ;
}
