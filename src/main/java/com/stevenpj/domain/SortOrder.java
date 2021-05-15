package com.stevenpj.domain;

import java.util.Comparator;

public enum SortOrder {
    ASCENDING(comparator -> comparator),
    DESCENDING(Comparator::reversed);

    private final ComparatorDecorator decorator;

    SortOrder(ComparatorDecorator decorator) {
        this.decorator = decorator;
    }

    public Comparator<Munro> applyTo(Comparator<Munro> other) {
        return this.decorator.applyTo(other);
    }

    @FunctionalInterface
    interface ComparatorDecorator {
        Comparator<Munro> applyTo(Comparator<Munro> comparator);
    }
}
