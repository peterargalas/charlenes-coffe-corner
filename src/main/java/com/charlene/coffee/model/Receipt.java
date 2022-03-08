package com.charlene.coffee.model;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(String name, BigDecimal total, List<LineItem> lineItems) {
    public boolean hasLineItems() {
        return lineItems != null && !lineItems.isEmpty();
    }
}
