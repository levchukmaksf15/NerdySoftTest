package org.example;

import java.time.LocalDate;

public class ProductFactory {

    static public RealProduct createRealProduct(String name, double price, double size, int weight) {
        return new RealProduct(name, price, size, weight);
    }

    static public VirtualProduct createVirtualProduct(String name, double price, String code, LocalDate expirationDate) {
        return new VirtualProduct(name, price, code, expirationDate);
    }
}