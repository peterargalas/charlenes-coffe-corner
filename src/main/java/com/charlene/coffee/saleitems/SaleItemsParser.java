package com.charlene.coffee.saleitems;

import com.charlene.coffee.saleitems.Coffee.Extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.charlene.coffee.saleitems.Coffee.Extra.*;

/**
 * Parses input as written by a person and returns a collection of SaleItems.
 */
public class SaleItemsParser {
    private static final Pattern coffeePattern = Pattern.compile("(small|medium|large) coffee(?: with )?(.*)?");

    /**
     * Parses a string of SaleItems and returns the corresponding SaleItems. The parser is not case sensitive.
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
        List<SaleItem> items = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(input, ",");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            SaleItem saleItem = switch (token.toLowerCase()) {
                case "bacon roll" -> new BaconRoll();
                case "orange juice" -> new OrangeJuice();
                default -> parseCoffee(token.toLowerCase());
            };

            items.add(saleItem);
        }

        return items.toArray(new SaleItem[]{});
    }

    private static SaleItem parseCoffee(String input) {
        Matcher matcher = coffeePattern.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("I'm sorry, I don't understand '" + input + "'");
        }

        String sizeString = matcher.group(1).toUpperCase();
        Coffee.Size size = Coffee.Size.valueOf(sizeString);

        Extra[] extras = parseExtras(matcher.group(2));

        return new Coffee(size, extras);
    }

    private static Extra[] parseExtras(String input) {
        // Scanner used to switch on a string, not a single character.
        Scanner scanner = new Scanner(input).useDelimiter(" and ");

        List<Extra> extras = new ArrayList<>();

        while (scanner.hasNext()) {
            String token = scanner.next();

            Extra extra = switch (token.toLowerCase()) {
                case "extra milk" -> EXTRA_MILK;
                case "special roast" -> SPECIAL_ROAST;
                case "foamed milk" -> FOAMED_MILK;
                default -> throw new IllegalArgumentException("I'm sorry, I couldn't find a " + token + " as a Coffee extra");
            };

            extras.add(extra);
        }

        return extras.toArray(new Extra[]{});
    }
}
