package org.example.spring_aop;

import org.example.spring_aop.service.MemberService;
import org.example.spring_aop.service.OrderService;
import org.example.spring_aop.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);

        OrderService orderService = context.getBean(OrderService.class);
        MemberService memberService = context.getBean(MemberService.class);
        ProductService productService = context.getBean(ProductService.class);

        System.out.println("\n========== 1. order service ==========");
        System.out.println(orderService.placeOrder("a"));

        System.out.println("\n========== 2. member service ==========");
        System.out.println(memberService.register("kim"));

        System.out.println("\n========== 3. product service ==========");
        System.out.println(productService.getProduct("A-100"));

        System.out.println("\n========== 4. order service class ==========");
        System.out.println(orderService.getClass().getName());

        context.close();
    }
}
