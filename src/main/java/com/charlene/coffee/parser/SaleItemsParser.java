package com.charlene.coffee.saleitems;

import com.charlene.coffee.model.saleitem.BaconRoll;
import com.charlene.coffee.model.saleitem.Coffee;
import com.charlene.coffee.model.saleitem.Coffee.Extra;
import com.charlene.coffee.model.saleitem.OrangeJuice;
import com.charlene.coffee.model.saleitem.SaleItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Parses input as written by a person and returns a collection of SaleItems.
 */
public class SaleItemsParser {
    // Yes, "Some people, when confronted with a problem, think "I know, I'll use regular expressions." Now they have two problems."
    // This matches a size, the coffee literal and captures an optional with extras section.
    private static final String SALE_ITEM_SEPARATOR = ",";
    private static final String COFFEE_EXTRAS_SEPARATOR = " and ";

    private static final ArrayList<String> ROLL_VARIATIONS = new ArrayList<>(
      List.of("bacon roll", "bacon", "roll")
    );
    private static final ArrayList<String> JUICE_VARIATIONS = new ArrayList<>(
      List.of("orange juice", "juice", "fresh")
    );
    private static final Pattern COFFEE_PATTERN = Pattern.compile("(small|medium|large) coffee(?: with )?(.*)?");
    /**
     * Parses a string of SaleItems and returns the corresponding SaleItems. The parser is not case-sensitive.
     * <p>
     * Expected input: a comma separated list of sale items.
     * Accepted tokens:
     * bacon roll
     * orange juice
     * small|medium|large coffee( with extra)( and extra)( and extra)
     * <p>
     * An example of a valid input string is:
     * bacon roll, bacon roll, orange juice, large coffee with extra milk and special roast and foamed milk
     */
    public static SaleItem[] parse(String input) {
        return Arrays.stream(input.split(SALE_ITEM_SEPARATOR))
            // remove multiple wihitespaces with single space, trim and transform to lowercase
            .map(item -> item.replaceAll("\\s{2,}", " ").trim().toLowerCase())
            // map normalized string to SaleItem
            .map(item -> {
                if (ROLL_VARIATIONS.contains(item)) return new BaconRoll();
                if (JUICE_VARIATIONS.contains(item)) return new OrangeJuice();

                Matcher matcher = COFFEE_PATTERN.matcher(item);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException(format("I'm sorry, I was expecting a coffee. I don't understand '%s'", item));
                }

                String sizeString = matcher.group(1);
                Coffee.Size size = Coffee.Size.valueOf(sizeString.toUpperCase());

                List<Extra> extras = parseExtras(matcher.group(2));

                // cast to SaleItem is redundat, but is used to avoid code chekc warning
                return (SaleItem) new Coffee(size, extras);
          })
          .toArray(SaleItem[]::new);
    }

    /**
     * Parses coffee extras. Expected input is eg "special roast and foamed milk"
     *
     * @param input lower case String
     */
    private static List<Extra> parseExtras(String input) {

        return Arrays.stream(input.split(COFFEE_EXTRAS_SEPARATOR))
            .filter(Predicate.not(String::isBlank))
            .map(extra -> Arrays.stream(Coffee.Extra.values())
                .filter(extraLiteral ->
                    extraLiteral.compactName().equals(extra.replaceAll("\\s+", ""))
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("I'm sorry, I couldn't find a '%s' as a Coffee extra", extra))))
            .toList();
    }
}
