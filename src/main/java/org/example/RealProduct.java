package org.example;

import java.util.Objects;

public class RealProduct extends Product{

    private double size;
    private int weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealProduct that)) return false;
        return Double.compare(that.size, size) == 0 && Double.compare(that.weight, weight) == 0;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, weight);
    }

    public RealProduct(String name, double price, double size, int weight) {
        super(name, price);
        this.size = size;
        this.weight = weight;
    }
}
