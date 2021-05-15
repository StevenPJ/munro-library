package com.stevenpj.infra

import com.stevenpj.domain.HillCategory

import com.stevenpj.domain.Munro
import com.stevenpj.domain.MunroCriteria
import com.stevenpj.domain.MunroRepository
import spock.lang.Specification

import static com.stevenpj.builder.MunroCriteriaBuilder.munroCriteria

class InMemoryMunroRepositorySpec extends Specification {

    MunroRepository repository = new InMemoryMunroRepository()

    def "should return all munros with non blank hillCategory by default"() {
        given:
        repository.save(MUNROE)
        repository.save(MUNROE_TOP)

        expect:
        repository.findAll(new MunroCriteria()) == [
                MUNROE,
                MUNROE_TOP
        ]
    }

    def "should filter by hill category"() {
        given:
        repository.save(MUNROE)
        repository.save(MUNROE_TOP)

        when:
        def result = repository.findAll(munroCriteria().with(category).build())

        then:
        result.collect{it.hillCategory} == matched

        where:
        category               | matched
        HillCategory.MUNRO     | ["MUN"]
        HillCategory.MUNRO_TOP | ["TOP"]
        HillCategory.EITHER    | ["MUN", "TOP"]
    }

    def "should filter blank hillCategories"() {
        given:
        def blank = Munro.builder().build()
        repository.save(MUNROE)
        repository.save(blank)

        when:
        def result = repository.findAll(munroCriteria().with(HillCategory.EITHER).build())

        then:
        !result.contains(blank)
    }

    def "should limit number of returned results"() {
        given:
        repository.save(MUNROE)
        repository.save(MUNROE_TOP)

        when:
        def result = repository.findAll(munroCriteria().limit(1).build())

        then:
        result.size() == 1
    }

    def "should ignore limit if limit > the number of munros"() {
        given:
        repository.save(MUNROE)
        repository.save(MUNROE_TOP)

        when:
        def result = repository.findAll(munroCriteria().limit(3).build())

        then:
        result.size() == 2
    }

    static final Munro MUNROE = Munro.builder().hillCategory("MUN").build()
    static final Munro MUNROE_TOP= Munro.builder().hillCategory("TOP").build()
}
