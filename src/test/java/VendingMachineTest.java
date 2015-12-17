import com.vendingmachine.MoneyFactory;
import com.vendingmachine.ProductFactory;
import com.vendingmachine.VendingMachine;
import com.vendingmachine.dto.*;
import com.vendingmachine.exceptions.*;
import junit.framework.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineTest {
    VendingMachine vendingMachine;

    @BeforeTest
    public void setUp() {
        vendingMachine = new VendingMachine("Brand new company");
    }

    @Test
    public void updateProductList() {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product());
        vendingMachine.setProducts(productList);
        org.testng.Assert.assertNotEquals(vendingMachine.getProducts().size(), 0);
    }

    @Test
    public void vendingMachineCoinAcceptanceTestWithMoney_Success() throws VendingMachineCoinAcceptanceException {
        Money money = new Money();
        vendingMachine.insertCoin(money);
        Assert.assertEquals(vendingMachine.getAmount(), money);
    }

    @Test
    public void vendingMachineCoinAcceptanceTest_Success() throws VendingMachineCoinAcceptanceException {
        vendingMachine.insertCoin(Euro.ONEEURO, Cent.FIFTYCENT);
        Assert.assertEquals(vendingMachine.getAmount().inCents(), 150);
    }

    @Test(expectedExceptions = VendingMachineCoinAcceptanceException.class)
    public void vendingMachineCoinAcceptanceTest_FailureWithWrongCoin() throws VendingMachineCoinAcceptanceException {
        vendingMachine.insertCoin(Euro.FIVEEURO);
    }

    @Test
    public void moneyFactoryCoinAcceptanceTest_Success() throws VendingMachineCoinAcceptanceException {
        Assert.assertEquals(MoneyFactory.getMoney(Euro.ONEEURO, Cent.FIFTYCENT).getCents(), 50);
        Assert.assertEquals(MoneyFactory.getMoney(Euro.ONEEURO, Cent.FIFTYCENT).getEuros(), 1);

    }

    @Test(expectedExceptions = VendingMachineCoinAcceptanceException.class)
    public void moneyFactoryCoinAcceptanceTest_FailureWithOtherCoin() throws VendingMachineCoinAcceptanceException {
        ICoin dollar = new ICoin() {
            public int getAmount() {
                return 0;
            }
        };
        MoneyFactory.getMoney(dollar);
    }

    @Test(expectedExceptions = VendingMachineCoinAcceptanceException.class)
    public void vendingMachineCoinAcceptanceTest_FailureWithNotSupportedEuros() throws VendingMachineCoinAcceptanceException {
        MoneyFactory.getMoney(Euro.FIVEEURO);
    }

    @Test
    public void returnMoneyTest() throws VendingMachineCoinAcceptanceException {
        Money money = MoneyFactory.getMoney(Euro.ONEEURO, Cent.FIFTYCENT);

        vendingMachine.insertCoin(money);
        vendingMachine.returnMoney();
        Assert.assertEquals(vendingMachine.getAmount(), null);

    }


    @Test
    public void buyOneProductAtOnceTest_Success() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(MoneyFactory.getMoney(Euro.ONEEURO, Cent.FIFTYCENT));
        Assert.assertNotNull(vendingMachine.buy(1));
    }

    @Test(expectedExceptions = VendingMachineOnePurchaseAtOnceException.class)
    public void buyOneProductAtOnceTest_Failure() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(MoneyFactory.getMoney(Euro.ONEEURO, Cent.FIFTYCENT));
        vendingMachine.buy(1);
        vendingMachine.buy(2);
    }

    @Test
    public void productFactoryTest_Success() throws VendingMachineCoinAcceptanceException, NotSupportedProductException {
        Assert.assertNotNull(ProductFactory.getInstance().getProduct(1));
    }

    @Test(expectedExceptions = NotSupportedProductException.class)
    public void productFactoryTest_Failure() throws VendingMachineCoinAcceptanceException, NotSupportedProductException {
        Assert.assertNotNull(ProductFactory.getInstance().getProduct(11));
    }

    @Test
    public void productTest() throws VendingMachineCoinAcceptanceException, NotSupportedProductException {
        Product product = ProductFactory.getInstance().getProduct(1);
        Assert.assertEquals(product.getName(), "Snickers XL");
        Assert.assertEquals(product.getPrice().inCents(), 120);
    }

    @Test
    public void buyExistingProductWithEqualMoney() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(Euro.ONEEURO, Cent.TWENTYCENT);
        org.testng.Assert.assertEquals(vendingMachine.buy(1).toString(), ProductFactory.getInstance().getProduct(1).toString());

    }

    @Test
    public void buyExistingProductWithMoreMoney() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(Euro.TWOEUROS, Cent.FIFTYCENT);
        org.testng.Assert.assertEquals(vendingMachine.buy(1).toString(), ProductFactory.getInstance().getProduct(1).toString());

    }

    @Test(expectedExceptions = InsufficientAmountOfMoneyException.class)
    public void buyExistingProductWithLessMoney() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(Cent.FIFTYCENT);
        vendingMachine.buy(1);

    }

    @Test(expectedExceptions = InsufficientAmountOfProductException.class)
    public void buyNotExistingProduct() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(Euro.TWOEUROS);
        List<Product> productList = vendingMachine.getProducts();
        productList.get(0).setAvailable(0);
        productList.get(1).setAvailable(0);
        productList.get(2).setAvailable(0);
        vendingMachine.setProducts(productList);
        vendingMachine.buy(1);

    }

    @Test(expectedExceptions = NotSupportedProductException.class)
    public void notSupportedProductBuy() throws VendingMachineCoinAcceptanceException, InsufficientAmountOfMoneyException, InsufficientAmountOfProductException, NotSupportedProductException, VendingMachineOnePurchaseAtOnceException {
        vendingMachine.insertCoin(Euro.TWOEUROS);
        vendingMachine.buy(11);

    }


}
