package com.stevenpj.domain;

import java.util.Comparator;
import lombok.Getter;

public enum SortBy {
    None((m, o) -> 0),
    heightInMeters(Comparator.comparingInt(Munro::getHeightInMeters)),
    name(Comparator.comparing(Munro::getName));

    @Getter
    private final Comparator<Munro> comparator;

    SortBy(Comparator<Munro> comparator) {
        this.comparator = comparator;
    }
}
