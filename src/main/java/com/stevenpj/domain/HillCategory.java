package com.stevenpj.domain;

import lombok.Getter;

public enum HillCategory {
    MUNRO("MUN");

    private final String value;

    HillCategory(String value) {
        this.value = value;
    }

    public boolean matches(Munro munro) {
        return this.value.equals(munro.getHillCategory());
    }
}
