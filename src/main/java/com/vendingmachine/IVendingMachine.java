package com.vendingmachine;

import com.vendingmachine.dto.Money;
import com.vendingmachine.dto.Product;
import com.vendingmachine.exceptions.InsufficientAmountOfMoneyException;
import com.vendingmachine.exceptions.InsufficientAmountOfProductException;
import com.vendingmachine.exceptions.NotSupportedProductException;
import com.vendingmachine.exceptions.VendingMachineOnePurchaseAtOnceException;

import java.util.List;

public interface IVendingMachine {
    String getManufacturer();

    Money getAmount();

    List<Product> getProducts();

    void insertCoin(Money amount);

    void returnMoney();

    Product buy(int productNumber) throws VendingMachineOnePurchaseAtOnceException, NotSupportedProductException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException;

}
