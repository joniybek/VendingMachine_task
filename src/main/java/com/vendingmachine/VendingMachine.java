package com.vendingmachine;

import com.vendingmachine.api.IVendingMachineCallForRefill;
import com.vendingmachine.api.IVendingMachineRefill;
import com.vendingmachine.dto.ICoin;
import com.vendingmachine.dto.Money;
import com.vendingmachine.dto.Product;
import com.vendingmachine.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine implements IVendingMachine, IVendingMachineRefill {
    private String manufacturer;
    private Money amount;
    private List<Product> productList;
    private IVendingMachineCallForRefill refillingServiceProvider;

    public VendingMachine(String manufacturer) {
        this.manufacturer = manufacturer;
        productList = new ArrayList<Product>(ProductFactory.getInstance().getAssortiment().values());

        //initially added products
        for (Product product : productList) {
            product.setAvailable(10);
            System.out.println("Vending Machine is initially filled with products Product: "
                    + product + " Available: " + product.getAvailable());
        }
    }

    public void refillProduct(int productId, int count) throws NotSupportedProductException {
        //api
        Product product = ProductFactory.getInstance()
                .getProduct(productId);
        product.setAvailable(product.getAvailable() + count);

    }

    // register observer to use api
    public void registerRefillingServiceProvider(IVendingMachineCallForRefill iVendingMachineCallForRefill) {
        this.refillingServiceProvider = iVendingMachineCallForRefill;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Money getAmount() {
        return this.amount;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public void setProducts(List<Product> productList) {
        this.productList = productList;
    }

    public void insertCoin(Money amount) {

        System.out.println("Money inserted " + amount);
        this.amount = amount;
    }

    //Overloaded method which accepts coins
    public void insertCoin(ICoin... coins) throws VendingMachineCoinAcceptanceException {
        try {
            this.amount = MoneyFactory.getMoney(coins);
            System.out.println("Money inserted " + this.amount);
        } catch (VendingMachineCoinAcceptanceException e) {
            returnMoney();
            throw new VendingMachineCoinAcceptanceException();
        }
    }

    public void returnMoney() {
        System.out.println("Returning money " + amount);
        this.amount = null;

    }

    public Product buy(int productNumber)
            throws VendingMachineOnePurchaseAtOnceException, NotSupportedProductException,
            InsufficientAmountOfMoneyException, InsufficientAmountOfProductException {
        if (amount == null) {
            throw new VendingMachineOnePurchaseAtOnceException();
        }
        Product product = null;
        for (Product productInLoop : productList) {
            if (productInLoop.getProductNumber() == productNumber) {
                product = productInLoop;
            }
        }
        if (product == null) {
            throw new NotSupportedProductException();
        }
        if (product.getPrice().inCents() > amount.inCents()) {
            throw new InsufficientAmountOfMoneyException();
        }
        if (product.getAvailable() < 1) {
            if (refillingServiceProvider != null) {
                refillingServiceProvider.notifyAboutUnavailability(productNumber);
            }
            throw new InsufficientAmountOfProductException();
        }

        product.decreaseAvailableAmount();
        int cents = amount.inCents();

        amount.setEuros(cents / 100 - product.getPrice().inCents() / 100);
        amount.setCents(cents - amount.getEuros() * 100 - product.getPrice().inCents());
        returnMoney();
        System.out.println("Product is bought " + product);
        return product;
    }
}
