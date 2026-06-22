package org.example.solid;

import java.util.ArrayList;
import java.util.List;

// ISP
interface Delivery {
    String deliverItem();
}

interface RoketDelivery {
    String roketItem();
}

interface Product {
    int getPrice();
}

// LSP
class Computer implements Product, Delivery, RoketDelivery {
    @Override
    public String deliverItem() {
       return "Computer delivered normal shipping (3-4 days)";
    }

    @Override
    public int getPrice() {
        return 1500000;
    }

    @Override
    public String roketItem() {
        return "Computer delivered roket shipping (Guaranteed for 2 days at the latest)";
    }
}

class Book implements Product, Delivery {
    @Override
    public String deliverItem() {
        return "Book delivered normal shipping (3-4 days)";
    }

    @Override
    public int getPrice() {
        return 10000;
    }
}

// OCP
interface Grade {
    int discountPrice(int price);
}

class NormalGrade implements Grade {
    @Override
    public int discountPrice(int price) {
        return price;
    }
}

class VipGrade implements Grade {
    @Override
    public int discountPrice(int price) {
        return (int) (price * 0.9);
    }
}

// DIP
interface Notification {
    void sendNotification(String message);
}

class Kakao implements Notification {
    @Override
    public void sendNotification(String message) {
        System.out.println("[Kakao] : " + message);
    }
}

class Sms implements Notification {
    @Override
    public void sendNotification(String message) {
        System.out.println("[Sms] : " + message);
    }
}

// SRP
class Order {
    private List<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getTotalPrice() {
        return products.stream().mapToInt(product -> product.getPrice()).sum();
    }
}

class orderManager {
    private Grade grade;
    private Notification notification;

    public orderManager(Grade grade, Notification notification) {
        this.grade = grade;
        this.notification = notification;
    }

    public void manage(Order order) {
        int totalPrice = order.getTotalPrice();
        int discountPrice = grade.discountPrice(totalPrice);

        System.out.println("\n======================");
        System.out.println("total price: " + totalPrice);
        System.out.println("discount price: " + discountPrice);
        System.out.println("=====================\n");

        for (Product product : order.getProducts()) {
            if (product instanceof RoketDelivery) {
                String message = ((RoketDelivery) product).roketItem();
                notification.sendNotification(message);
            } else if (product instanceof Delivery) {
                String message = ((Delivery) product).deliverItem();
                notification.sendNotification(message);
            }
        }
    }
}

public class OnlineStore {
    public static void main(String[] args) {
        Order order = new Order();

        order.add(new Computer());
        order.add(new Book());

        orderManager manager = new orderManager(new VipGrade(), new Kakao());
        manager.manage(order);
    }
}
