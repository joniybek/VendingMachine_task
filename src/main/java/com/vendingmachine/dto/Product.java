package com.vendingmachine.dto;

public class Product {
    private int available;
    private Money price;
    private String name;
    private int productNumber;

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public int getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Product name='" + name + ", price=" + price;
    }

    public void increaseAvailableAmount() {
        setAvailable(getAvailable() + 1);
    }

    public void decreaseAvailableAmount() {
        setAvailable(getAvailable() - 1);
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
