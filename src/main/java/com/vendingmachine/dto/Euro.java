package com.vendingmachine.dto;

public enum Euro implements ICoin {
    ONEEURO(1), TWOEUROS(2), FIVEEURO(5);

    private final int amount;

    Euro(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
