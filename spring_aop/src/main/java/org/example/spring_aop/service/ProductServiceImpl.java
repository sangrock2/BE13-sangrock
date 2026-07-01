package org.example.spring_aop.service;

public class ProductServiceImpl implements ProductService {
    @Override
    public String getProduct(String code) {
        sleep(30);
        return "product: " + code;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
