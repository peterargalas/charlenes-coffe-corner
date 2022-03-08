package com.charlene.coffee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * Verifies the creation of a receipt for Charlene's Coffee Corner
 */
public class CreateReceiptTest {
    private final ReceiptCreator receiptCreator = new ReceiptCreator();
    @Test
    void shouldPrintEmptyReceipt() {
        var receipt = receiptCreator.createReceipt(Collections.emptyList());

        Assertions.assertEquals(receipt.toString(), "Charlene's Coffee Corner");
    }
}
