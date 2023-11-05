package com.charlene.coffee;

import com.charlene.coffee.model.receipt.Receipt;
import com.charlene.coffee.printer.ReceiptPrinter;
import com.charlene.coffee.saleitems.SaleItemsParser;

import java.util.StringJoiner;

public class Main {
    private static final String USAGE = """
            ReceiptPrinter: No items specified.
                        
            Usage: Specify the items to create a receipt for, separated by commas (,), eg
            bacon roll, orange juice, large coffee with special roast and foamed milk
                        
            Valid items are:
            - bacon roll|bacon|roll
            - orange juice|juice|fresh
            - small|medium|large coffee [with <extras>]
                        
            valid extras are
            - foamed milk
            - extra milk
            - special roast
                        
            if you want more than one extra, add them with an "and".
            """;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(USAGE);
        }

        String input = joinCommandLineArgs(args);

        try {
            System.out.println(
                    ReceiptPrinter.print(
                            Receipt.fromItems(SaleItemsParser.parse(input))
                    )
            );
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String joinCommandLineArgs(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (String arg : args) {
            stringJoiner.add(arg);
        }

        return stringJoiner.toString();
    }
}
