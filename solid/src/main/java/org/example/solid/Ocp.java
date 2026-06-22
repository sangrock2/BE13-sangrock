package org.example.solid;

// 개방 폐쇄 원칙

public class Ocp {
    public interface DiscountPolicy {
        int discount(int price);
    }

    public class BasicDiscount implements DiscountPolicy {
        @Override
        public int discount(int price) {
            return price;
        }
    }

    public class SilverDiscount implements DiscountPolicy {
        @Override
        public int discount(int price) {
            return (int) (price * 0.95);
        }
    }

    public class GoldDiscount implements DiscountPolicy {
        @Override
        public int discount(int price) {
            return (int) (price * 0.9);
        }
    }

    public class VipDiscount implements DiscountPolicy {
        @Override
        public int discount(int price) {
            return (int) (price * 0.8);
        }
    }


}
