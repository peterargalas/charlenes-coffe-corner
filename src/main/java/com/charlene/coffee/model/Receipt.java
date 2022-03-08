package com.charlene.coffee.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Receipt(String name, BigDecimal total, String... items) {
    public String toString() {
        if (items.length > 0) {
            return name + sectionSeparator() + items[0] + sectionSeparator() + "Total " + total.setScale(2, RoundingMode.HALF_UP) + " CHF" + System.lineSeparator();
        }
        return name + System.lineSeparator();
    }

    private String sectionSeparator() {
        return System.lineSeparator() + System.lineSeparator();
    }
}
