package com.charlene.coffee;

import com.charlene.coffee.model.Item;
import com.charlene.coffee.model.Receipt;

import java.util.List;

/**
 * Facade to create a receipt from a list of items.
 *
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
    public Receipt createReceipt(List<Item> items) {
        return new Receipt("Charlene's Coffee Corner");
    }
}
