package com.stevenpj.domain;

import java.util.function.Predicate;
import lombok.Getter;

public enum HillCategory {
    MUNRO("MUN"::equals),
    MUNRO_TOP("TOP"::equals),
    EITHER(category -> true);

    private final Predicate<String> matcher;

    HillCategory(Predicate<String> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(String hillCategory) {
        return matcher.test(hillCategory);
    }
}
