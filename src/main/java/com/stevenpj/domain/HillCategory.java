package com.stevenpj.domain;

import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

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
