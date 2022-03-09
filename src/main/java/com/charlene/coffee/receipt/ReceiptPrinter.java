package com.charlene.coffee.receipt;

import java.math.RoundingMode;
import java.util.stream.Collectors;

/**
 * Knows how to convert a receipt to a printable string.
 */
public class ReceiptPrinter {
    private final static String SECTION_SEPARATOR = System.lineSeparator() + System.lineSeparator();

    private static String lineItemString(LineItem lineItem) {
        return String.format("%-60s %6s CHF", lineItem.description(), lineItem.price());
    }

    public static String print(Receipt receipt) {
        if (receipt.hasLineItems()) {
            String lineItemsString = receipt.lineItems()
                    .stream()
                    .map(ReceiptPrinter::lineItemString)
                    .collect(Collectors.joining(System.lineSeparator()));

            String totalsString = lineItemString(new LineItem("Total", receipt.total().setScale(2,
                    RoundingMode.HALF_UP))) + System.lineSeparator();

            return receipt.name() +
                    SECTION_SEPARATOR + lineItemsString +
                    SECTION_SEPARATOR + totalsString;
        }

        return receipt.name() + System.lineSeparator();
    }
}
