package com.vendingmachine.dto;

public enum Cent implements ICoin {
    FIVECENT(5), TENCENT(10), TWENTYCENT(20), FIFTYCENT(50);

    private final int amount;

    Cent(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

}
