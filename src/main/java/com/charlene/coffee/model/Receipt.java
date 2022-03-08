package com.charlene.coffee.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

public record Receipt(String name, BigDecimal total, String ... lineItems) {
    public String toString() {
        if (lineItems.length > 0) {
            String lineItemsString = Arrays.stream(lineItems).collect(Collectors.joining(System.lineSeparator()));
            return name + sectionSeparator() + lineItemsString + sectionSeparator() + "Total " + total.setScale(2, RoundingMode.HALF_UP) + " CHF" + System.lineSeparator();
        }
        return name + System.lineSeparator();
    }

    private String sectionSeparator() {
        return System.lineSeparator() + System.lineSeparator();
    }
}
