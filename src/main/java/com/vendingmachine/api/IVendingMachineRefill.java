package com.vendingmachine.api;

import com.vendingmachine.exceptions.NotSupportedProductException;

public interface IVendingMachineRefill {
    public void refillProduct(int productId, int amount) throws NotSupportedProductException;
}
