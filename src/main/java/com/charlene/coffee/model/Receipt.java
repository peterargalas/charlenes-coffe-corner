package com.charlene.coffee.model;

public record Receipt(String name) {
    public String toString() {
        return name;
    }
}
