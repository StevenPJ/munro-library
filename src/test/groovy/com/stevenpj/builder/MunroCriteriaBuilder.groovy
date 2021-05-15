package com.stevenpj.builder

import com.stevenpj.domain.HillCategory

import com.stevenpj.domain.MunroCriteria

class MunroCriteriaBuilder {
    private MunroCriteria munroCriteria = new MunroCriteria()

    static MunroCriteriaBuilder munroCriteria() {
        return new MunroCriteriaBuilder()
    }

    MunroCriteriaBuilder with(HillCategory hillCategory) {
        munroCriteria.hillCategory = hillCategory
        return this
    }

    MunroCriteriaBuilder limit(Integer limit) {
        munroCriteria.limit = limit
        return this
    }

    MunroCriteria build() {
        return munroCriteria
    }

}