package com.stevenpj.domain;

import io.micrometer.core.instrument.util.StringUtils;
import java.util.function.Predicate;

public enum HillCategory {
    MUNRO("MUN"::equals),
    MUNRO_TOP("TOP"::equals),
    EITHER(StringUtils::isNotBlank);

    private final Predicate<String> matcher;

    HillCategory(Predicate<String> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(String hillCategory) {
        return matcher.test(hillCategory);
    }
}
