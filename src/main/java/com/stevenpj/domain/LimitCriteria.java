package com.stevenpj.domain;

import lombok.Value;

@Value
public class LimitCriteria implements MunroCriteria {
    Integer limit;

    @Override
    public boolean matches(Munro munro) {
        throw new UnsupportedOperationException();
    }
}
