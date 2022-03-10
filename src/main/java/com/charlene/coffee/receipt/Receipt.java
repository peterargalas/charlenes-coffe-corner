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
 * Your task is to:
 * Write a simple program whose output is formatted like a receipt you would receive at a supermarket.
 * The input to the program is a list of products the shopper wants to purchase (large coffee with extra milk, small coffee with special roast,
 * bacon roll, orange juice)
 */
public record Receipt(String name, BigDecimal total, List<LineItem> lineItems) {
    public static Receipt fromItems(SaleItem... items) {
        List<LineItem> lineItems = toLineItems(items);
        return new Receipt("Charlene's Coffee Corner", calculateTotalPrice(lineItems), lineItems);
    }

    private static BigDecimal calculateTotalPrice(List<LineItem> items) {
        return items.stream().map(LineItem::price).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static List<LineItem> discounts(SaleItem... items) {
        return Stream.concat(
                        extrasDiscountBasedOnSnacks(items).stream(),
                        everyFifthBeverageFreeDiscounts(items).stream())
                .toList();
    }

    private static List<LineItem> everyFifthBeverageFreeDiscounts(SaleItem[] items) {
        List<LineItem> discountList = new ArrayList<>();
        long beveragesCount = Arrays.stream(items)
                .filter(SaleItem::isBeverage)
                .count();

        if (beveragesCount >= 5) {
            List<SaleItem> beveragesByDescendingPrice = new ArrayList<>(Arrays.stream(items)
                    .filter(SaleItem::isBeverage)
                    .sorted(Comparator.comparing(SaleItem::price).reversed())
                    .toList());

            LongStream.range(0, beveragesCount / 5).forEach(index -> {
                SaleItem saleItem = beveragesByDescendingPrice.get((int) index);
                discountList.add(new LineItem("Bonus: every fifth beverage free",
                        saleItem.price().multiply(BigDecimal.valueOf(-1))));
            });
        }
        return discountList;
    }

    private static List<LineItem> extrasDiscountBasedOnSnacks(SaleItem[] items) {
        long snackCount = Arrays.stream(items)
                .filter(SaleItem::isSnack)
                .count();

        List<Coffee.Extra> allExtrasByDescendingPrice =
                new ArrayList<>(
                        Arrays.stream(items)
                                .filter(item -> item instanceof Coffee)
                                .flatMap(item -> ((Coffee) item).extras().stream())
                                .sorted(Comparator.comparing(Coffee.Extra::getPrice)
                                        .reversed())
                                .toList());

        List<LineItem> discountList = new ArrayList<>();

        // Iterate for each snack.
        for (int i = 0; i < snackCount; i++) {
            // Do we have any extras left to discount?
            if (!allExtrasByDescendingPrice.isEmpty()) {
                Coffee.Extra extra = allExtrasByDescendingPrice.get(0);
                discountList.add(new LineItem(
                        "Discount: free extra with snack (" + extra.printableName() + ")",
                        extra.getPrice().multiply(BigDecimal.valueOf(-1))));

                allExtrasByDescendingPrice.remove(0);
            }
        }

        return discountList;
    }

    private static List<LineItem> toLineItems(SaleItem... items) {
        List<LineItem> discounts = discounts(items);
        return Stream.concat(Arrays.stream(items)
                .map(saleItem -> new LineItem(saleItem.description(), saleItem.price())), discounts.stream()).toList();
    }

    public boolean hasLineItems() {
        return lineItems != null && !lineItems.isEmpty();
    }
}
