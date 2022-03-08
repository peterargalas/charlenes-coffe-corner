package com.charlene.coffee.model;

import java.math.BigDecimal;

public class OrangeJuice implements Item {
    private static final BigDecimal PRICE = new BigDecimal("3.95");

    @Override
    public String description() {
        return "Freshly squeezed orange juice (0.25l)";
    }

    @Override
    public BigDecimal price() {
        return PRICE;
    }
}
