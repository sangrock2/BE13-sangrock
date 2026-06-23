package org.example.springioc;

import java.util.HashMap;
import java.util.Map;

public class CoffeeContainer {
    private MilkFrother milkFrother = new MilkFrother();
    private Map<String, CoffeeMaker> coffeeMakers = new HashMap<String, CoffeeMaker>();

    public CoffeeMaker getCoffeeMaker(String type) {
        if (coffeeMakers.containsKey(type)) {
            return coffeeMakers.get(type);
        }

        Bean bean = null;

        if ("colombia".equals(type)) {
            bean = new ColombiaBean();
        } else if ("ethiopia".equals(type)) {
            bean = new EthiopiaBean();
        }

        CoffeeMaker coffeeMaker = new CoffeeMaker(bean, milkFrother);
        coffeeMakers.put(type, coffeeMaker);
        return coffeeMaker;
    }
}
