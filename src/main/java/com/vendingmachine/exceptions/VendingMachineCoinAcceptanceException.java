package com.vendingmachine.exceptions;

public class VendingMachineCoinAcceptanceException extends Exception {


    public VendingMachineCoinAcceptanceException() {
        super("Vending Machine cannot accept inserted coins");
    }
}
