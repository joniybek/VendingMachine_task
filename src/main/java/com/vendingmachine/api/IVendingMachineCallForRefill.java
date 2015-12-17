package com.vendingmachine.api;

public interface IVendingMachineCallForRefill {
    public void notifyAboutUnavailability(int productId);
}
