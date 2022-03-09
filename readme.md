## Interpretations and notes from implementing the Coffee Corner application

### Current status

I believe in "Make it work, make it nice, make it perform (if needed)". Currently, this project is in the "make it work"
phase, where the requirements aren't implemented, eg there is no program yet, but the business logic as verified by
tests appears to work for the happy paths.

I am also in a position where I'd like to submit something today, therefore I've forwarded a link to this repository and
might continue working on it this evening (2022-03-09).

### TODO

- Implement program that reads purchased items from the command line and outputs the receipt to the console.
- Add code and tests for the unhappy paths
- Refactor code for increased clarity and possibly efficiency. Some of the stream operations would benefit from cleanup
  for readability and virtually no considerations have been made for performance. I think this makes sense given that
  we're printing a receipt for a coffee shop, meaning that the most likely scenario is that we have a few items per
  receipt.

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

Each item that can be ordered knows its own price. This is simplistic, but for this minor application I decided not to
implement a separate price list.

The receipt calculates prices, discounts etc, using static methods that could be placed anywhere. Since the class is
pretty short anyway and the logic is fairly simple I think that it's ok to have this code here, even though it wouldn't
be in a real application.

There is a lot of validation missing. For instance a coffee can have the same extras added multiple times. In other
words it's possible to add extra milk many times.

### Receipt layout

There is no transaction id or date printed on the receipt. I could have added this, but very little effort was spent on
making the receipt look nice except for aligning the amounts.

### Tests

All tests are based on the ReceiptPrinter output and have 100% coverage. Tests could have been written for each class,
but I chose to implement them on this level because I think that it makes sense to treat the receipt generation as a
black box that I send the input to and observe that the output is what is expected.

### Program

I haven't written a program that takes input from the command line and produces the receipt. The only way to currently
run the code is via the tests. I expect that adding the program with input parsing would be an extra hours work or so.
