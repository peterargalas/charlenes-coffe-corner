package com.charlene.coffee.saleitems;

import java.math.BigDecimal;

public class OrangeJuice implements SaleItem {
    private static final BigDecimal PRICE = new BigDecimal("3.95");

    @Override
    public String description() {
        return "Freshly squeezed orange juice (0.25l)";
    }

    @Override
    public BigDecimal price() {
        return PRICE;
    }

    @Override
    public Type type() {
        return Type.BEVERAGE;
    }
}
