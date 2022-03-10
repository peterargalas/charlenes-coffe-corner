package com.charlene.coffee.receipt;

import com.charlene.coffee.saleitems.Coffee;
import com.charlene.coffee.saleitems.SaleItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Facade to create a receipt from a list of lineItems.
 * <p>
 * Requirements at the time of the original implementation.
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
 * <p>
 * The input to the program is a list of products the shopper wants to purchase (large coffee with extra milk, small coffee with special roast,
 * bacon roll, orange juice)
 */
public record Receipt(String header, BigDecimal totalPrice, List<LineItem> lineItems) {
    public static Receipt fromItems(SaleItem... items) {
        List<LineItem> lineItems = toLineItems(items);
        return new Receipt("Charlene's Coffee Corner", calculateTotalPrice(lineItems), lineItems);
    }

    private static BigDecimal calculateTotalPrice(List<LineItem> items) {
        return items.stream()
                .map(LineItem::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Stream<LineItem> discounts(SaleItem... items) {
        return Stream.concat(
                extrasDiscountBasedOnSnacks(items),
                everyFifthBeverageFreeDiscounts(items));
    }

    private static Stream<LineItem> everyFifthBeverageFreeDiscounts(SaleItem... items) {
        long beveragesCount = Arrays.stream(items)
                .filter(SaleItem::isBeverage)
                .count();

        if (beveragesCount < 5) {
            return Stream.empty();
        }

        List<SaleItem> beveragesByDescendingPrice = Arrays.stream(items)
                .filter(SaleItem::isBeverage)
                .sorted(Comparator.comparing(SaleItem::price).reversed())
                .toList();

        return LongStream.range(0, beveragesCount / 5).mapToObj(index -> {
            SaleItem saleItem = beveragesByDescendingPrice.get((int) index);
            return new LineItem("Bonus: every fifth beverage free", saleItem.price().negate());
        });
    }

    private static Stream<LineItem> extrasDiscountBasedOnSnacks(SaleItem[] items) {
        long snackCount = Arrays.stream(items)
                .filter(SaleItem::isSnack)
                .count();

        List<Coffee.Extra> allExtrasByDescendingPrice =
                Arrays.stream(items)
                        // If the item is a Coffee, extract all the extras.
                        .flatMap(item -> item instanceof Coffee c ? c.extras().stream() : Stream.empty())
                        .sorted(Comparator.comparing(Coffee.Extra::getPrice).reversed())
                        .toList();

        List<LineItem> discountList = new ArrayList<>();

        LongStream.range(0, snackCount).forEach(index -> {
            // Do we have any extras left to discount?
            if (index < allExtrasByDescendingPrice.size()) {
                Coffee.Extra extra = allExtrasByDescendingPrice.get((int) index);
                discountList.add(
                        new LineItem("Discount: free extra with snack (" + extra.printableName() + ")", extra.getPrice().negate()));
            }
        });

        return discountList.stream();
    }

    /**
     * Takes SaleItems and returns LineItems corresponding to the SaleItem and adds discount items if applicable.
     */
    private static List<LineItem> toLineItems(SaleItem... items) {
        return Stream.concat(
                Arrays.stream(items).map(saleItem -> new LineItem(saleItem.description(), saleItem.price())),
                discounts(items)
        ).toList();
    }

    public boolean hasLineItems() {
        return lineItems != null && !lineItems.isEmpty();
    }
}
