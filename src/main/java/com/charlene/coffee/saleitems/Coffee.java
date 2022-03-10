package com.charlene.coffee.saleitems;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Represents a coffee of a certain size with optional extras.
 */
public record Coffee(Size size, List<Extra> extras) implements SaleItem {
    public Coffee(Size size, Extra... extras) {
        this(size, Arrays.stream(extras).toList());
    }

    @Override
    public String description() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(size.name().toLowerCase());

        extras.stream().map(Extra::printableName).forEach(joiner::add);
        return "Coffee (" + joiner + ")";
    }

    @Override
    public BigDecimal price() {
        BigDecimal priceOfExtras = extras.stream().map(extra -> extra.price).reduce(BigDecimal.ZERO, BigDecimal::add);
        return size.price.add(priceOfExtras);
    }

    @Override
    public Type type() {
        return Type.BEVERAGE;
    }

    public enum Size {
        SMALL(new BigDecimal("2.50")),
        MEDIUM(new BigDecimal("3.00")),
        LARGE(new BigDecimal("3.50"));

        private final BigDecimal price;

        Size(BigDecimal price) {
            this.price = price;
        }
    }

    public enum Extra {
        EXTRA_MILK(new BigDecimal("0.30")),
        FOAMED_MILK(new BigDecimal("0.50")),
        SPECIAL_ROAST(new BigDecimal("0.90"));

        private final BigDecimal price;

        Extra(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public String printableName() {
            return name().toLowerCase().replace('_', ' ');
        }
    }
}
