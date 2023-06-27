package org.example;

import java.time.LocalDate;

public class VirtualProduct extends Product{

    private final String code;
    private final LocalDate expirationDate;

    public VirtualProduct(String name, double price, String code, LocalDate expirationDate) {
        super(name, price);
        this.code = code;
        this.expirationDate = expirationDate;
    }
}