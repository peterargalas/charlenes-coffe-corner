package com.charlene.coffee.model;

import java.math.BigDecimal;

/**
 * An item purchased by a customer in the coffee shop.
 */
public interface Item {
    String description();
    BigDecimal price();
}
