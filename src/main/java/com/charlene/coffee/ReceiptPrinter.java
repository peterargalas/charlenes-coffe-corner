package com.charlene.coffee;

import com.charlene.coffee.model.Receipt;

import java.math.RoundingMode;
import java.util.stream.Collectors;

/**
 * Knows how to convert a receipt to a printable string.
 */
public class ReceiptPrinter {
    private final static String SECTION_SEPARATOR = System.lineSeparator() + System.lineSeparator();

    public static String print(Receipt receipt) {
        if (receipt.hasLineItems()) {
            String lineItemsString = receipt.lineItems()
                    .stream()
                    .map(item -> item.description() + " " + item.price() + " CHF")
                    .collect(Collectors.joining(System.lineSeparator()));

            return receipt.name()
                    + SECTION_SEPARATOR + lineItemsString
                    + SECTION_SEPARATOR + "Total " + receipt.total().setScale(2, RoundingMode.HALF_UP) + " CHF" + System.lineSeparator();
        }
        return receipt.name() + System.lineSeparator();
    }
}
