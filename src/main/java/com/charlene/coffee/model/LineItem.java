package com.charlene.coffee.model;

import java.math.BigDecimal;

/**
 * A receipt line item, specifying something with a price.
 */
public record LineItem(String description, BigDecimal price) {
}
