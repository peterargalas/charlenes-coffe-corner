# Implementation notes
Before I started to implement this task I did a small analysis, where I have removed customer stamp card from requirements 
as there is no requirement to support card evidence and without persistence it would be hard to implement.

As a next step I did a quick research if there isn't any solution for this task already done by someone. 
Reason why I did so is I don't like to reinvent the wheel and I always take some (reasonably long) time 
to check whether there's a solution for a problem.

By chance, I found a solution for this task prepared by David Durant. I went throught the code and found out it is a very
simple and well implemented solution. So it did not make sense to me to write it once again. 
I rather tried to identify places for enhancement and refactoring. My findings and enhancements are summarized in next section.

## Refactoring

### Project structure

I have created packages for model classes, to better separate data (model) classes from classes implementing business
logic. I have created two packages: 
- model.saleitem for coffee corner sale items classes
- model.receipt for receipt classes

Remaining classes where moved to packages, that better represents their behaviour and purpose
- SaleItemsParser was moved to parser package
- ReceiptPrinter was moved to printer package

### Robustness update

I have improved the implementation robustness by addind support for item name alternatives and support for 
accepting consequent whitespaces in the input string.

Example inputs that are now supported:
- "juice", "orange juice", "fresh" as an alterantive for orange juice
- "small  coffe" and "small    coffe" are both recognized as "small coffee"

### Removal of Tokenizer and Scanner

In service parser I have replaced (from my point of view unnecessary) usage of Tokenizer and Scanner,
with String.split and Stream API.

I have also replaced explicit mappings of extras input to Extra enumeration literal with iteration over Extra enumeration
values and comparing compacted literal name with the input string. This approch provides better support for adding new literals
to Extra enumeration without necessity to modify the parser.

### Minor changes

I have extracted string constants used directly in code into static constants to improve readability and maintenance.

---
Original readme from David Durrant <david@durrant.se>
## Interpretations and notes from implementing the Coffee Corner application

### How to run

- Build with `mvn package`
- Run from command line with `java -jar target/coffee-1.0-SNAPSHOT.jar large coffee with extra milk`

### Current status

I believe in "Make it work, make it nice, make it perform (if needed)". Currently, this project has passed the "make it
nice"
phase, where all the requirements are implemented. It could probably benefit from an iteration or two of cleanup, for
instance after getting PR comments. In my opinion it is good enough for a PR.

I don't think the code needs to be improved for performance considering how it will be used.

### Bonus program

Since no persistence was to be used there is no state regarding the bonus program stamping and no provision has been
made to support that the customer may already have purchased four beverages prior to the current purchase. This part of
the spec has been implemented as
"Every fifth beverage in the current order will be free."

Since Charlene cares about her customers, the most expensive beverages will be those given as free. If the beverage is a
coffee the extras will also be free.

### Discounts

I have interpreted the requirements for discounts to read as "For each snack, an extra is free". The extras discounts
are given for the most expensive extras since that is what benefits the customers the most and I think that Charlene
wants to build loyalty with her customers.

### TDD implementation

A commit was performed after each feature was added. The tests were written first, keeping the code simple and
refactoring as the need arose.

### Simplicity

I have tried to maintain a balance between simple code and modelling concepts from the domain. Prices have been
implemented as BigDecimal to keep precision, but for this application a double would have worked as well, or an int to
represent the minor currency unit.

Each item that can be ordered knows its own price. This is simplistic, but for this simple application I decided not to
implement a separate price list.

The receipt calculates prices, discounts etc, using static methods that could be placed anywhere. Since the class is
pretty short anyway and the logic is fairly simple I think that it's ok to have this code here, even though it wouldn't
be in a real application.

There is validation missing. For instance a coffee can have the same extras added multiple times. In other words it's
possible to add extra milk many times. Which might be ok or not. Adding two special roasts probably doesn't make sense,
but is currently allowed.

### Receipt layout

There is no transaction id or date printed on the receipt. I could have added this, but little effort was spent on
making the receipt look nice except for aligning the amounts.

The coffee extras are included with the coffee they belong to. They could have been separate, but I tried both and
preferred them the way I implemented it.

Items could be grouped by type on the receipt, with a count and an item price. I decided against this since I'm assuming
that they typical order will be very small and the additional code complexity wouldn't be worth it. Something to
consider after the MVP.

### Tests

I haven't added tests to each class, since I prefer a black box approach where possible. This means adding input to the
system and observing that the system as a whole returns the expected output. For instance, by testing the ReceiptPrinter
output the test asserts that the correct output is produced. If for instance the price calculation was off the correct
output wouldn't be produced. This allows me to refactor the underlying implementation without having to update any tests
as long as the code still produces the same output. In a more complex application where it would be harder to test all
paths with black box testing it would make sense to add additional types of tests.

One drawback with this implementation is that tests will have to be updated when the receipt layout changes. The tests
should be fairly easy to update if this would happen. If this would happen frequently it would be worth considering to
test the Receipt content instead.
