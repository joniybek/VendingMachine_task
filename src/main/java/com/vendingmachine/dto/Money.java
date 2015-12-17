package com.vendingmachine.dto;

public class Money {

    public int getEuros() {
        return euros;
    }

    public void setEuros(int euros) {
        this.euros = euros;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public int inCents() {
        return getCents() + getEuros() * 100;
    }

    private int euros;
    private int cents;

    @Override
    public String toString() {
        return euros + " EUR, "
                + cents + " CENTS";
    }
}
