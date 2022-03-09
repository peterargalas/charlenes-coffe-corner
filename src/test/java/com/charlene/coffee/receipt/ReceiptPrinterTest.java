package com.charlene.coffee.receipt;

import com.charlene.coffee.saleitems.BaconRoll;
import com.charlene.coffee.saleitems.Coffee;
import com.charlene.coffee.saleitems.OrangeJuice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.charlene.coffee.saleitems.Coffee.Extras.*;
import static com.charlene.coffee.saleitems.Coffee.Size.*;

/**
 * Verifies the creation of a receipt for Charlene's Coffee Corner
 */
public class ReceiptPrinterTest {
    private static final String RECEIPT_FOR_NO_ITEMS = """
            Charlene's Coffee Corner
            """;
    private static final String RECEIPT_FOR_ONE_SMALL_COFFEE = """
            Charlene's Coffee Corner
                        
            Coffee (small)                                                 2.50 CHF
                        
            Total                                                          2.50 CHF
            """;
    private static final String RECEIPT_FOR_ONE_MEDIUM_COFFEE_BACON_ROLL_AND_ORANGE_JUICE = """
            Charlene's Coffee Corner
                        
            Coffee (medium)                                                3.00 CHF
            Bacon roll                                                     4.50 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
                        
            Total                                                         11.45 CHF
            """;
    private static final String RECEIPT_FOR_ONE_MEDIUM_COFFEE_WITH_EXTRAS = """
            Charlene's Coffee Corner

            Coffee (medium, extra milk, foamed milk, special roast)        4.70 CHF

            Total                                                          4.70 CHF
            """;

    private static final String RECEIPT_FOR_MEDIUM_COFFEE_WITH_EXTRA_AND_BACON_ROLLS = """
            Charlene's Coffee Corner
                        
            Coffee (medium, foamed milk, special roast)                    4.40 CHF
            Bacon roll                                                     4.50 CHF
            Bacon roll                                                     4.50 CHF
            Bacon roll                                                     4.50 CHF
            Discount: free extra with snack (special roast)               -0.90 CHF
            Discount: free extra with snack (foamed milk)                 -0.50 CHF
                        
            Total                                                         16.50 CHF
            """;

    private static final String RECEIPT_FOR_BEVERAGE_DISCOUNT = """
            Charlene's Coffee Corner
                        
            Coffee (medium, foamed milk, extra milk, special roast)        4.70 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
            Coffee (large, foamed milk, special roast)                     4.90 CHF
            Coffee (large, foamed milk, special roast, extra milk)         5.20 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
            Coffee (medium, foamed milk, special roast)                    4.40 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
            Coffee (medium, special roast)                                 3.90 CHF
            Freshly squeezed orange juice (0.25l)                          3.95 CHF
            Bonus: every fifth beverage free                              -5.20 CHF
            Bonus: every fifth beverage free                              -4.90 CHF
                        
            Total                                                         36.70 CHF
            """;

    private static final String RECEIPT_FOR_TWO_COFFEES = """
            Charlene's Coffee Corner
                        
            Coffee (small)                                                 2.50 CHF
            Coffee (large)                                                 3.50 CHF
                        
            Total                                                          6.00 CHF
            """;

    @Test
    void shouldPrintEmptyReceipt() {
        var receipt = Receipt.fromItems();

        Assertions.assertEquals(RECEIPT_FOR_NO_ITEMS, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptWithSingleCoffee() {
        var receipt = Receipt.fromItems(new Coffee(SMALL));

        Assertions.assertEquals(RECEIPT_FOR_ONE_SMALL_COFFEE, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptWithTwoCoffees() {
        var receipt = Receipt.fromItems(new Coffee(SMALL), new Coffee(LARGE));

        Assertions.assertEquals(RECEIPT_FOR_TWO_COFFEES, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptWithCoffeeBaconRollAndOrangeJuice() {
        var receipt = Receipt.fromItems(new Coffee(MEDIUM), new BaconRoll(), new OrangeJuice());

        Assertions.assertEquals(RECEIPT_FOR_ONE_MEDIUM_COFFEE_BACON_ROLL_AND_ORANGE_JUICE, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldPrintReceiptForCoffeeWithExtras() {
        var receipt = Receipt.fromItems(new Coffee(MEDIUM, EXTRA_MILK, FOAMED_MILK,
                SPECIAL_ROAST));

        Assertions.assertEquals(RECEIPT_FOR_ONE_MEDIUM_COFFEE_WITH_EXTRAS, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldGetExtrasDiscountForSnack() {
        var receipt = Receipt.fromItems(
                new Coffee(MEDIUM, FOAMED_MILK, SPECIAL_ROAST),
                new BaconRoll(),
                new BaconRoll(),
                new BaconRoll()
        );

        Assertions.assertEquals(RECEIPT_FOR_MEDIUM_COFFEE_WITH_EXTRA_AND_BACON_ROLLS, ReceiptPrinter.print(receipt));
    }

    @Test
    void shouldGetTwoBeveragesForFreeWhenPurchasingEleven() {
        var receipt = Receipt.fromItems(
                new Coffee(MEDIUM, FOAMED_MILK, EXTRA_MILK, SPECIAL_ROAST),
                new OrangeJuice(),
                new OrangeJuice(),
                new OrangeJuice(),
                new Coffee(LARGE, FOAMED_MILK, SPECIAL_ROAST),
                new Coffee(LARGE, FOAMED_MILK, SPECIAL_ROAST, EXTRA_MILK),
                new OrangeJuice(),
                new Coffee(MEDIUM, FOAMED_MILK, SPECIAL_ROAST),
                new OrangeJuice(),
                new Coffee(MEDIUM, SPECIAL_ROAST),
                new OrangeJuice()
        );

        Assertions.assertEquals(RECEIPT_FOR_BEVERAGE_DISCOUNT, ReceiptPrinter.print(receipt));
    }
}
