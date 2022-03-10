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
    // Yes, "Some people, when confronted with a problem, think "I know, I'll use regular expressions." Now they have two problems."
    // This matches a size, the coffee literal and captures an optional with extras section.
    private static final Pattern coffeePattern = Pattern.compile("(small|medium|large) coffee(?: with )?(.*)?");

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
        List<SaleItem> items = new ArrayList<>();

        StringTokenizer itemTokenizer = new StringTokenizer(input, ",");

        while (itemTokenizer.hasMoreTokens()) {
            String itemString = itemTokenizer.nextToken().toLowerCase().trim();

            // For readability, the value of the switch expression could be extracted into a variable. I like it this
            // way.
            items.add(switch (itemString) {
                case "bacon roll" -> new BaconRoll();
                case "orange juice" -> new OrangeJuice();
                default -> parseCoffee(itemString);
            });
        }

        return items.toArray(new SaleItem[]{});
    }

    private static SaleItem parseCoffee(String input) {
        Matcher matcher = coffeePattern.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("I'm sorry, I was expecting a coffee. I don't understand '" + input +
                    "'");
        }

        String sizeString = matcher.group(1);
        Coffee.Size size = Coffee.Size.valueOf(sizeString.toUpperCase());

        List<Extra> extras = parseExtras(matcher.group(2));

        return new Coffee(size, extras);
    }

    /**
     * Parses coffee extras. Expected input is eg "special roast and foamed milk"
     *
     * @param input lower case String
     */
    private static List<Extra> parseExtras(String input) {
        // Scanner used to switch on a string, not a single character.
        Scanner scanner = new Scanner(input).useDelimiter(" and ");

        List<Extra> extras = new ArrayList<>();

        while (scanner.hasNext()) {
            String extraToken = scanner.next();

            extras.add(switch (extraToken.toLowerCase()) {
                case "extra milk" -> EXTRA_MILK;
                case "foamed milk" -> FOAMED_MILK;
                case "special roast" -> SPECIAL_ROAST;
                default -> throw new IllegalArgumentException("I'm sorry, I couldn't find a " + extraToken + " as a Coffee extra");
            });
        }

        return extras;
    }
}
