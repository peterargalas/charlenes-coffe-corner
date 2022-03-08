package com.charlene.coffee;

import com.charlene.coffee.model.Coffee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.charlene.coffee.model.Coffee.Size.SMALL;

/**
 * Verifies the creation of a receipt for Charlene's Coffee Corner
 */
public class CreateReceiptTest {
    private final ReceiptCreator receiptCreator = new ReceiptCreator();

    @Test
    void shouldPrintEmptyReceipt() {
        var receipt = receiptCreator.createReceipt();

        Assertions.assertEquals(RECEIPT_FOR_NO_ITEMS, receipt.toString());
    }

    @Test
    void shouldPrintReceiptWithSingleCoffee() {
        var receipt = receiptCreator.createReceipt(new Coffee(SMALL));

        Assertions.assertEquals(RECEIPT_FOR_ONE_SMALL_COFFEE, receipt.toString());
    }

    private static final String RECEIPT_FOR_NO_ITEMS = """
            Charlene's Coffee Corner
            """;

    private static final String RECEIPT_FOR_ONE_SMALL_COFFEE = """
            Charlene's Coffee Corner
                
            Coffee (small) 2.50 CHF
                
            Total 2.50 CHF
            """;
}
