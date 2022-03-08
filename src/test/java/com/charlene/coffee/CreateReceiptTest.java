package com.charlene.coffee;

import com.charlene.coffee.model.BaconRoll;
import com.charlene.coffee.model.Coffee;
import com.charlene.coffee.model.OrangeJuice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.charlene.coffee.model.Coffee.Size.*;

/**
 * Verifies the creation of a receipt for Charlene's Coffee Corner
 */
public class CreateReceiptTest {
    private final ReceiptCreator receiptCreator = new ReceiptCreator();

    @Test
    void shouldPrintEmptyReceipt() {
        var receipt = receiptCreator.createReceipt();

        Assertions.assertEquals(RECEIPT_FOR_NO_ITEMS, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptWithSingleCoffee() {
        var receipt = receiptCreator.createReceipt(new Coffee(SMALL));

        Assertions.assertEquals(RECEIPT_FOR_ONE_SMALL_COFFEE, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptWithTwoCoffees() {
        var receipt = receiptCreator.createReceipt(new Coffee(SMALL), new Coffee(LARGE));

        Assertions.assertEquals(RECEIPT_FOR_TWO_COFFEES, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptWithCoffeBaconRollAndOrangeJuice() {
        var receipt = receiptCreator.createReceipt(new Coffee(MEDIUM), new BaconRoll(), new OrangeJuice());

        Assertions.assertEquals(RECEIPT_FOR_ONE_MEDIUM_COFFEE_BACON_ROLL_AND_ORANGE_JUICE, ReceiptPrinter.print(receipt));
    }

    private static final String RECEIPT_FOR_NO_ITEMS = """
            Charlene's Coffee Corner
            """;

    private static final String RECEIPT_FOR_ONE_SMALL_COFFEE = """
            Charlene's Coffee Corner
                
            Coffee (small) 2.50 CHF
                
            Total 2.50 CHF
            """;

    private static final String RECEIPT_FOR_ONE_MEDIUM_COFFEE_BACON_ROLL_AND_ORANGE_JUICE = """
            Charlene's Coffee Corner
                
            Coffee (medium) 3.00 CHF
            Bacon roll 4.50 CHF
            Freshly squeezed orange juice (0.25l) 3.95 CHF
                
            Total 11.45 CHF
            """;

    private static final String RECEIPT_FOR_TWO_COFFEES = """
            Charlene's Coffee Corner
                
            Coffee (small) 2.50 CHF
            Coffee (large) 3.50 CHF
                
            Total 6.00 CHF
            """;
}
