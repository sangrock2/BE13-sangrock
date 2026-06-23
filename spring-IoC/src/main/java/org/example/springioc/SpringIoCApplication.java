package org.example.springioc;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIoCApplication {

    public static void main(String[] args) {
        CoffeeContainer coffeeContainer = new CoffeeContainer();

        CoffeeMaker colombiaMaker = coffeeContainer.getCoffeeMaker("colombia");
        CoffeeMaker ethiopiaMaker = coffeeContainer.getCoffeeMaker("ethiopia");

        colombiaMaker.brew();
        ethiopiaMaker.brew();

        Button button = new Button();
        LikeAction likeAction = new LikeAction();

        button.setClickListener(likeAction);
        button.press();
    }

}
