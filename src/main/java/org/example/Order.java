package org.example;

import java.util.List;

public class Order {

    private User user;
    private List<Product> productList;

    private Order() {

    }

    private Order(User user, List<Product> productList) {
        this.user = user;
        this.productList = productList;
    }

    static public Order createOrder(User user, List<Product> productList) {
        return new Order(user, productList);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", productList=" + productList +
                '}';
    }
}