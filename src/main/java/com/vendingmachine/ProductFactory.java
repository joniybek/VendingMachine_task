package com.vendingmachine;

import com.vendingmachine.dto.Cent;
import com.vendingmachine.dto.Euro;
import com.vendingmachine.dto.Money;
import com.vendingmachine.dto.Product;
import com.vendingmachine.exceptions.NotSupportedProductException;
import com.vendingmachine.exceptions.VendingMachineCoinAcceptanceException;

import java.util.HashMap;

public class ProductFactory {
    public static HashMap<Integer, Product> getAssortiment() {
        return cachedProducts;
    }

    static HashMap<Integer, Product> cachedProducts;
    static int count;


    static ProductFactory productFactory;

    private ProductFactory() {
        cachedProducts = new HashMap<Integer, Product>();
        count = 0;

        Product chocolate = new Product();
        Product cookies = new Product();
        Product drink = new Product();
        chocolate.setName("Snickers XL");
        cookies.setName("Laima");
        drink.setName("Fanta");
        chocolate.setProductNumber(++count);
        cookies.setProductNumber(++count);
        drink.setProductNumber(++count);

        try {
            chocolate.setPrice(MoneyFactory.getMoney(Euro.ONEEURO, Cent.TWENTYCENT));
            cookies.setPrice(MoneyFactory.getMoney(Cent.FIFTYCENT));
            drink.setPrice(MoneyFactory.getMoney(Cent.FIFTYCENT, Cent.TWENTYCENT));
        } catch (VendingMachineCoinAcceptanceException e) {
            throw new AssertionError("At this point there should be no VendingMachineCoinAcceptanceException");
        }


        cachedProducts.put(chocolate.getProductNumber(), chocolate);
        cachedProducts.put(cookies.getProductNumber(), cookies);
        cachedProducts.put(drink.getProductNumber(), drink);
        System.out.println("Product factory created cache of products");

    }

    public static ProductFactory getInstance() {
        if (productFactory == null) {
            return new ProductFactory();
        }
        return productFactory;
    }

    public Product createProduct(String name, Money money) {
        Product product = new Product();
        product.setName(name);
        product.setProductNumber(++count);
        product.setPrice(money);
        cachedProducts.put(product.getProductNumber(), product);
        return product;
    }

    public Product getProduct(int productId) throws NotSupportedProductException {

        if (cachedProducts.containsKey(productId)) {
            Product product = cachedProducts.get(productId);
            System.out.println(product + " is created");
            return cachedProducts.get(productId);
        } else {
            throw new NotSupportedProductException();
        }

    }

}
