package com.charlene.coffee.saleitems;

import org.junit.jupiter.api.Test;

import static com.charlene.coffee.saleitems.Coffee.Extra.*;
import static com.charlene.coffee.saleitems.Coffee.Size.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleItemsParserTest {
    @Test
    void parseFullString() {
        SaleItem[] MAX_SALE_ITEMS = {
                new BaconRoll(),
                new BaconRoll(),
                new OrangeJuice(),
                new Coffee(LARGE, EXTRA_MILK, SPECIAL_ROAST, FOAMED_MILK)
        };

        SaleItem[] saleItems = SaleItemsParser.parse("bacon roll, bacon roll, orange juice, large coffee with extra milk and special roast and foamed milk");

        assertArrayEquals(MAX_SALE_ITEMS, saleItems);
    }

    @Test
    void parseCoffeeWithoutAnyExtras() {
        SaleItem[] saleItems = SaleItemsParser.parse("small coffee");

        assertArrayEquals(new SaleItem[]{new Coffee(SMALL)}, saleItems);
    }

    @Test
    void parseCoffeeWithOneExtra() {
        SaleItem[] saleItems = SaleItemsParser.parse("small coffee with special roast");

        assertArrayEquals(new SaleItem[]{new Coffee(SMALL, SPECIAL_ROAST)}, saleItems);
    }

    @Test
    void parseCoffeeWithTwoExtras() {
        SaleItem[] saleItems = SaleItemsParser.parse("medium coffee with foamed milk and special roast");

        assertArrayEquals(new SaleItem[]{new Coffee(MEDIUM, FOAMED_MILK, SPECIAL_ROAST)}, saleItems);
    }

    @Test
    void parseInvalidStringShouldFail() {
        String invalidString = "bacon rolls";

        assertThrows(IllegalArgumentException.class, () -> SaleItemsParser.parse(invalidString));
    }

    @Test
    void parseInvalidCoffeeExtraShouldFail() {
        String invalidString = "large coffee with cinnamon";

        assertThrows(IllegalArgumentException.class, () -> SaleItemsParser.parse(invalidString));
    }
}