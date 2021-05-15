package com.stevenpj.domain;

import lombok.Value;

@Value
public class HillCategoryCriteria implements MunroCriteria {
    HillCategory hillCategory;

    @Override
    public boolean matches(Munro munro) {
        return hillCategory.matches(munro.getHillCategory());
    }
}
