package com.charlene.coffee.model;

import java.math.BigDecimal;

public class BaconRoll implements Item {
    private static final BigDecimal PRICE = new BigDecimal("4.50");

    @Override
    public String description() {
        return "Bacon roll";
    }

    @Override
    public BigDecimal price() {
        return PRICE;
    }
}
