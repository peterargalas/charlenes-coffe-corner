package com.charlene.coffee.model;

import java.math.BigDecimal;

public record Coffee(Size size) implements Item {
    private static final BigDecimal SMALL_PRICE = new BigDecimal("2.50");
    private static final BigDecimal MEDIUM_PRICE = new BigDecimal("3.00");
    private static final BigDecimal LARGE_PRICE = new BigDecimal("3.50");
    @Override
    public String description() {
        return "Coffee (" + size.name().toLowerCase() + ")";
    }

    @Override
    public BigDecimal price() {
        return switch (size) {
            case SMALL -> SMALL_PRICE;
            case MEDIUM -> MEDIUM_PRICE;
            case LARGE -> LARGE_PRICE;
        };
    }

    public enum Size {
        SMALL, MEDIUM, LARGE
    }
}
