package com.charlene.coffee.model;

public record Coffee(Size size) implements Item{
    @Override
    public String description() {
        return "Coffee (" + size.name().toLowerCase() + ")";
    }

    public enum Size {
        SMALL, MEDIUM, LARGE
    }
}
