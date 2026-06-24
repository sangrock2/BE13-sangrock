package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(Arrays.asList(
            new Product("a", 100), new Product("b", 500), new Product("c", 1000),
            new Product("d", 1500), new Product("e", 2000)
        ));

        System.out.println("\n========== 1. Basic Stream  ==========");
        products.forEach(product -> System.out.println("name : " + product.getName() + ", price : " + product.getPrice()));

        System.out.println("\n========== 2. Filter Stream  ==========");
        products.stream().filter(product -> product.getPrice() >= 1000).forEach(product -> System.out.println("name : " + product.getName() + ", price : " + product.getPrice()));

        System.out.println("\n========== 3. Map Stream  ==========");
        products.stream().map(Product::getName).forEach(name -> System.out.println("name : " + name));

        System.out.println("\n========== 4. Map VS FlatMap  ==========");

        List<Order> orders = new ArrayList<>(Arrays.asList(
                new Order(1, Arrays.asList("a", "b", "c")), new Order(2, Arrays.asList("d", "e"))
        ));

        orders.stream().map(Order::getItems).forEach(System.out::println);
        orders.stream().flatMap(order -> order.getItems().stream()).forEach(System.out::println);

        System.out.println("\n========== 5. Filter + Map + Collect  ==========");
        List<String> collect = products.stream().filter(product -> product.getPrice() >= 1000)
                .map(Product::getName)
                .toList();

        System.out.println(collect);

        System.out.println("\n========== 6. Count / Sum / Average / Sorted  ==========");

        long count = products.stream().filter(product -> product.getPrice() >= 1000).count();
        System.out.println("count : " + count);

        int sum = products.stream().mapToInt(Product::getPrice).sum();
        System.out.println("sum : " + sum);

        int average = products.stream().mapToInt(Product::getPrice).sum() / products.size();
        System.out.println("average : " + average);

        List<String> sorted = products.stream().sorted(Comparator.comparing(Product::getPrice)).map(Product::getName).toList();
        System.out.println("sorted : " + sorted);

        System.out.println("\n========== 7. Advanced 1 ==========");
        List<String> under_500 = products.stream().filter(product -> product.getPrice() <= 500)
                .map(Product::getName)
                .toList();
        System.out.println("under_500 : " + under_500);

        System.out.println("\n========== 8. Advanced 2  ==========");
        products.stream().map(product -> product.getName() + ": " + product.getPrice() + " KRW" ).forEach(System.out::println);

        System.out.println("\n========== 9. Advanced 3  ==========");
        String maxProduct = products.stream().map(Product::getName).max(String::compareTo).get();
        System.out.println("maxProduct : " + maxProduct);

        System.out.println("\n========== 10. Advanced 4  ==========");
        List<String> distinct = orders.stream().flatMap(order -> order.getItems().stream()).distinct().sorted().toList();
        System.out.println("distinct : " + distinct);

        System.out.println("\n========== 11. Advanced 5  ==========");
        Double averagingInt = products.stream().collect(Collectors.averagingInt(Product::getPrice));
        Map<String, List<String>> groupingBy = products.stream().collect(Collectors
                .groupingBy(product -> product.getPrice() > averagingInt ? "UP" : "DOWN", Collectors.mapping(Product::getName, Collectors.toList()))
        );

        System.out.println("groupingBy : " + groupingBy);


        System.out.println("\n========== 12. Advanced 6  ==========");
        int reduce = products.stream().reduce(0, (s, p) -> s + p.getPrice(), Integer::sum);
        System.out.println("reduce : " + reduce);

    }
}