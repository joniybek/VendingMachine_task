package com.vendingmachine.exceptions;

public class VendingMachineOnePurchaseAtOnceException extends Exception {
    public VendingMachineOnePurchaseAtOnceException() {
        super("Cannot buy more than one product at once");
    }
}
