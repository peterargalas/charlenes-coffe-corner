package com.charlene.coffee.saleitems;

import com.charlene.coffee.model.saleitem.BaconRoll;
import com.charlene.coffee.model.saleitem.Coffee;
import com.charlene.coffee.model.saleitem.OrangeJuice;
import com.charlene.coffee.model.saleitem.SaleItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.charlene.coffee.model.saleitem.Coffee.Extra.*;
import static com.charlene.coffee.model.saleitem.Coffee.Size.*;
import static com.charlene.coffee.saleitems.SaleItemsParser.parse;
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

        SaleItem[] saleItems = parse("bacon roll, bacon roll, orange juice, large coffee with extra milk and special roast and foamed milk");

        assertArrayEquals(MAX_SALE_ITEMS, saleItems);
    }

    @Test
    void parseCoffeeWithoutAnyExtras() {
        SaleItem[] saleItems = parse("small coffee");

        assertArrayEquals(new SaleItem[]{new Coffee(SMALL)}, saleItems);
    }

    @Test
    void parseCoffeeWithOneExtra() {
        SaleItem[] saleItems = parse("small coffee with special roast");

        assertArrayEquals(new SaleItem[]{new Coffee(SMALL, SPECIAL_ROAST)}, saleItems);
    }

    @Test
    void parseCoffeeWithTwoExtras() {
        SaleItem[] saleItems = parse("medium coffee with foamed milk and special roast");

        assertArrayEquals(new SaleItem[]{new Coffee(MEDIUM, FOAMED_MILK, SPECIAL_ROAST)}, saleItems);
    }

    @Test
    void parseInvalidStringShouldFail() {
        String invalidInput = "bacon rolls";

        assertThrows(IllegalArgumentException.class, () -> parse(invalidInput));
    }

    @Test
    void parseInvalidCoffeeExtraShouldFail() {
        String invalidInput = "large coffee with cinnamon";

        assertThrows(IllegalArgumentException.class, () -> parse(invalidInput));
    }

    @Test
    void parseJuiceVariations() {
        ArrayList<String> variations = new ArrayList<>(
          List.of("orange juice", "juice", "fresh")
        );

        variations.forEach((juice) -> {
            SaleItem[] saleItems = parse(juice);
            assertArrayEquals(new SaleItem[]{new OrangeJuice()}, saleItems);
        });
    }

    @Test
    void parseBaconRollVariations() {
        ArrayList<String> variations = new ArrayList<>(
          List.of("bacon roll", "bacon", "roll")
        );

        variations.forEach((juice) -> {
            SaleItem[] saleItems = parse(juice);
            assertArrayEquals(new SaleItem[]{new BaconRoll()}, saleItems);
        });
    }
}