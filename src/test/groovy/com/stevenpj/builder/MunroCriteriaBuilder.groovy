package com.stevenpj.builder

import com.stevenpj.domain.HillCategory

import com.stevenpj.domain.MunroCriteria
import com.stevenpj.domain.SortOrder

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

    MunroCriteriaBuilder minHeight(Integer minHeight) {
        munroCriteria.minHeight = minHeight
        return this
    }

    MunroCriteriaBuilder maxHeight(Integer maxHeight) {
        munroCriteria.maxHeight = maxHeight
        return this
    }

    MunroCriteriaBuilder sort(String sort) {
        munroCriteria.sort = sort
        return this
    }

    MunroCriteriaBuilder sortOrder(SortOrder sortOrder) {
        munroCriteria.sortOrder = sortOrder
        return this
    }

    MunroCriteria build() {
        return munroCriteria
    }
}