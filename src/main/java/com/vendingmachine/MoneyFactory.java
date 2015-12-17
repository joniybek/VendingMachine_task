package com.vendingmachine;

import com.vendingmachine.dto.Cent;
import com.vendingmachine.dto.Euro;
import com.vendingmachine.dto.ICoin;
import com.vendingmachine.dto.Money;
import com.vendingmachine.exceptions.VendingMachineCoinAcceptanceException;

import java.util.HashSet;
import java.util.Set;

public class MoneyFactory {
    private static Set<ICoin> acceptableCoins;

    static {
        //initialize with some products
        acceptableCoins = new HashSet<ICoin>();
        acceptableCoins.add(Euro.TWOEUROS);
        acceptableCoins.add(Euro.ONEEURO);
        acceptableCoins.add(Cent.FIFTYCENT);
        acceptableCoins.add(Cent.TWENTYCENT);
        acceptableCoins.add(Cent.TENCENT);
        acceptableCoins.add(Cent.FIVECENT);
    }

    public static Money getMoney(ICoin... coins) throws VendingMachineCoinAcceptanceException {
        int euros = 0;
        int cents = 0;
        for (ICoin coin : coins) {
            if (coin instanceof Euro && isAcceptable(coin)) {
                euros += coin.getAmount();
            } else if (coin instanceof Cent && isAcceptable(coin)) {
                cents += coin.getAmount();
            } else {
                throw new VendingMachineCoinAcceptanceException();
            }
        }
        Money money = new Money();
        money.setCents(cents);
        money.setEuros(euros);
        return money;

    }

    public static boolean isAcceptable(ICoin coin) {
        return acceptableCoins.contains(coin);
    }


}
