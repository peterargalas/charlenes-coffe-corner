package com.charlene.coffee;

import com.charlene.coffee.receipt.Receipt;
import com.charlene.coffee.receipt.ReceiptPrinter;
import com.charlene.coffee.saleitems.SaleItem;
import com.charlene.coffee.saleitems.SaleItemsParser;

import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (String arg : args) {
            stringJoiner.add(arg);
        }

        String input = stringJoiner.toString();

        SaleItem[] saleItems = SaleItemsParser.parse(input);

        System.out.println("Usage: " + System.getProperty("sun.java.command") + " coffee shop items");
        System.out.println(input);
        System.out.println(ReceiptPrinter.print(Receipt.fromItems(saleItems)));
    }
}
