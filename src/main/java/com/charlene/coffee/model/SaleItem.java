package com.charlene.coffee.model;

import java.math.BigDecimal;

/**
 * An item purchased by a customer in the coffee shop.
 */
public interface SaleItem {
    String description();

    BigDecimal price();

    Type type();

    default boolean isBeverage() {
        return type() == Type.BEVERAGE;
    }

    default boolean isSnack() {
        return type() == Type.SNACK;
    }

    enum Type {
        SNACK, BEVERAGE,
    }
}
