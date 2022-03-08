package com.charlene.coffee;

import com.charlene.coffee.model.Coffee;
import com.charlene.coffee.model.Item;
import com.charlene.coffee.model.Receipt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.charlene.coffee.model.Coffee.Size.LARGE;
import static com.charlene.coffee.model.Coffee.Size.SMALL;

/**
 * Facade to create a receipt from a list of lineItems.
 * <p>
 * Her Offering
 * - Coffee (small, medium, large) 2.50 CHF, 3.00 CHF, 3.50 CHF
 * - Bacon Roll 4.50 CHF
 * - Freshly squeezed orange juice (0.25l) 3.95 CHF
 * Extras:
 * - Extra milk 0.30 CHF
 * - Foamed milk 0.50 CHF
 * - Special roast coffee 0.90 CHF
 * Bonus Program
 * Charlene's idea is to attract as many regular‘s as possible to have a steady turnaround.
 * She decides to offer a customer stamp card, where every 5th beverage is for free.
 * If a customer orders a beverage and a snack, one of the extra's is free.
 * Your task is to:
 * Write a simple program whose output is formatted like a receipt you would receive at a supermarket.
 * The input to the program is a list of products the shopper wants to purchase (large coffee with extra milk, small coffee with special roast,
 * bacon roll, orange juice)
 */
public class ReceiptCreator {
    public Receipt createReceipt(Item... items) {
        if (items.length == 0) {
            return new Receipt("Charlene's Coffee Corner", BigDecimal.ZERO);
        }

        return new Receipt("Charlene's Coffee Corner", price(items), lines(items).toArray(new String[]{}));
    }

    private BigDecimal price(Item... items) {
        return Arrays.stream(items).map(this::toPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal toPrice(Item item) {
        return switch (item) {
            case Coffee c && c.size() == SMALL -> new BigDecimal("2.50");
            case Coffee c && c.size() == LARGE -> new BigDecimal("3.50");
            default -> BigDecimal.ZERO;
        };
    }

    private List<String> lines(Item... items) {
        return Arrays.stream(items).map(item -> item.description() + " " + toPrice(item) + " CHF").toList();
    }
}
