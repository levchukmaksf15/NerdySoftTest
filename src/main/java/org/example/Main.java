package org.example;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        //TODO Create User class with method createUser
        // User class fields: name, age;
        // Notice that we can only create user with createUser method without using constructor or builder
        User user1 = User.createUser("Alice", 32);
        User user2 = User.createUser("Bob", 19);
        User user3 = User.createUser("Charlie", 20);
        User user4 = User.createUser("John", 27);

        //TODO Create factory that can create a product for a specific type: Real or Virtual
        // Product class fields: name, price
        // Product Real class additional fields: size, weight
        // Product Virtual class additional fields: code, expiration date

        Product realProduct1 = ProductFactory.createRealProduct("Product A", 20.50, 10, 25);
        Product realProduct2 = ProductFactory.createRealProduct("Product B", 50, 6, 17);

        Product virtualProduct1 = ProductFactory.createVirtualProduct("Product C", 100, "xxx", LocalDate.of(2023, 5, 12));
        Product virtualProduct2 = ProductFactory.createVirtualProduct("Product D", 81.25, "yyy",  LocalDate.of(2024, 6, 20));


        //TODO Create Order class with method createOrder
        // Order class fields: User, List<Price>
        // Notice that we can only create order with createOrder method without using constructor or builder
        List<Order> orders = new ArrayList<>() {{
            add(Order.createOrder(user1, List.of(realProduct1, virtualProduct1, virtualProduct2)));
            add(Order.createOrder(user2, List.of(realProduct1, realProduct2)));
            add(Order.createOrder(user3, List.of(realProduct1, virtualProduct2)));
            add(Order.createOrder(user4, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2)));
        }};

        //TODO 1). Create singleton class which will check the code is used already or not
        // Singleton class should have the possibility to mark code as used and check if code used
        // Example:
        // singletonClass.useCode("xxx")
        // boolean isCodeUsed = virtualProductCodeManager.isCodeUsed("xxx") --> true;
        // boolean isCodeUsed = virtualProductCodeManager.isCodeUsed("yyy") --> false;
        // System.out.println("1. Create singleton class VirtualProductCodeManager \n");
        VirtualProductCodeManager virtualProductCodeManager = VirtualProductCodeManager.getInstance();
        //virtualProductCodeManager.useCode("yyy");
        virtualProductCodeManager.useCode("xxx");
        var isUsed = virtualProductCodeManager.isCodeUsed("yyy");
        System.out.println("Is code used: " + isUsed + "\n");

        //TODO 2). Create a functionality to get the most expensive ordered product
        Product mostExpensive = getMostExpensiveProduct(orders);
        System.out.println("2. Most expensive product: " + mostExpensive + "\n");

        //TODO 3). Create a functionality to get the most popular product(product bought by most users) among users
        Product mostPopular = getMostPopularProduct(orders);
        System.out.println("3. Most popular product: " + mostPopular + "\n");

        //TODO 4). Create a functionality to get average age of users who bought realProduct2
        double averageAge = calculateAverageAge(realProduct2, orders);
        System.out.println("4. Average age is: " + averageAge + "\n");

        //TODO 5). Create a functionality to return map with products as keys and a list of users
        // who ordered each product as values
        Map<Product, List<User>> productUserMap = getProductUserMap(orders);
        System.out.println("5. Map with products as keys and list of users as value \n");
        productUserMap.forEach((key, value) -> System.out.println("key: " + key + " " + "value: " +  value + "\n"));

        //TODO 6). Create a functionality to sort/group entities:
        // a) Sort Products by price
        // b) Sort Orders by user age in descending order
        List<Product> productsByPrice = sortProductsByPrice(List.of(realProduct1, realProduct2, virtualProduct1, virtualProduct2));
        System.out.println("6. a) List of products sorted by price: " + productsByPrice + "\n");
        List<Order> ordersByUserAgeDesc = sortOrdersByUserAgeDesc(orders);
        System.out.println("6. b) List of orders sorted by user age in descending order: " + ordersByUserAgeDesc + "\n");

        //TODO 7). Calculate the total weight of each order
        Map<Order, Integer> result = calculateWeightOfEachOrder(orders);
        System.out.println("7. Calculate the total weight of each order \n");
        result.forEach((key, value) -> System.out.println("order: " + key + " " + "total weight: " +  value + "\n"));
    }

    private static Map<Order, Integer> calculateWeightOfEachOrder(List<Order> orders) {

        if (orders == null) return null;

        Map<Order, Integer> weightOfEachOrderMap = new HashMap<>();

        for (Order order : orders) {

            int weightOfOrder = Objects.requireNonNull(order.getProductList()).stream().filter(p -> p instanceof RealProduct)
                    .mapToInt(p -> ((RealProduct) p).getWeight()).sum();

            weightOfEachOrderMap.put(order, weightOfOrder);
        }

        return weightOfEachOrderMap;
    }

    private static List<Order> sortOrdersByUserAgeDesc(List<Order> orders) {

        return orders != null
               ? orders.stream().sorted((o1, o2) -> o2.getUser().getAge() - o1.getUser().getAge()).toList()
               : null;
    }

    private static List<Product> sortProductsByPrice(List<Product> products) {

        return products != null
               ? products.stream().sorted((p1, p2) -> (int) (p1.getPrice() - p2.getPrice())).toList()
               : null;
    }

    private static Map<Product, List<User>> getProductUserMap(List<Order> orders) {

        if (orders == null) return null;

        Map<Product, List<User>> productUsersMap = new HashMap<>();

        for (Order order : orders) {

            List<Product> allUniqueProductInOrder = Objects.requireNonNull(order.getProductList()).stream().distinct().toList();

            for (Product product : allUniqueProductInOrder) {

                if (productUsersMap.putIfAbsent(product, new ArrayList<User>() {{add(order.getUser());}}) != null) {
                    productUsersMap.get(product).add(order.getUser());
                }
            }
        }

        return productUsersMap;
    }

    public static Product getMostExpensiveProduct(List<Order> orders) {

        if (orders == null) return null;

        Product mostExpensiveProduct = null;

        for (Order order : orders) {

            List<Product> products = order.getProductList();

            if (products == null) return null;

            Product mostExpensiveProductInOrder = products.stream().max((p1, p2) -> (int) (p1.getPrice() - p2.getPrice()))
                    .orElse(null);

            if (mostExpensiveProduct == null) {
                mostExpensiveProduct = mostExpensiveProductInOrder;
            } else if (Objects.requireNonNull(mostExpensiveProductInOrder).getPrice() > mostExpensiveProduct.getPrice()) {
                mostExpensiveProduct = products.get(0);
            }
        }

        return mostExpensiveProduct;
    }

    private static Product getMostPopularProduct(List<Order> orders) {

        if (orders == null) return null;

        Map<Product, Integer> popularProductsInOrder = new HashMap<>();

        for (Order order : orders) {

            List<Product> products = order.getProductList();
            if (products == null) return null;

            List<Product> allUniqueProductsInOrder = products.stream().distinct().toList();

            for (Product productFromUniqueProductsList : allUniqueProductsInOrder) {

                int numberOfProducts = (int) products.stream()
                        .filter(product -> product.getName().equals(productFromUniqueProductsList.getName()))
                        .count();

                if (popularProductsInOrder.putIfAbsent(productFromUniqueProductsList, numberOfProducts) != null) {
                    popularProductsInOrder.replace(productFromUniqueProductsList,
                            popularProductsInOrder.get(productFromUniqueProductsList) + numberOfProducts);
                }
            }

        }

        return Objects.requireNonNull(popularProductsInOrder.entrySet().stream().max((p1, p2) -> p1.getValue() - p2.getValue())
                .orElse(null))
                .getKey();
    }

    private static double calculateAverageAge(Product product, List<Order> orders) {

        if (orders == null || product == null) return Double.NaN;

        double sumOfAge = 0d;
        int numberOfUsers = 0;

        for (Order order : orders) {
            if (Objects.requireNonNull(order.getProductList()).stream().anyMatch(p -> p.equals(product))) {
                sumOfAge += order.getUser().getAge();
                numberOfUsers ++;
            }
        }

        return sumOfAge / numberOfUsers;
    }
}