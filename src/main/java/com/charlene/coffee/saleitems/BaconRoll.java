package com.charlene.coffee.saleitems;

import java.math.BigDecimal;

public record BaconRoll() implements SaleItem {
    private static final BigDecimal PRICE = new BigDecimal("4.50");

    @Override
    public String description() {
        return "Bacon roll";
    }

    @Override
    public BigDecimal price() {
        return PRICE;
    }

    @Override
    public Type type() {
        return Type.SNACK;
    }
}
