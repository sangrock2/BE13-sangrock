package org.example.spring_aop.service;

public class OrderServiceImpl implements OrderService {
    @Override
    public String placeOrder(String item) {
        sleep(80);                       // 실제 작업 흉내
        return "order: " + item;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}