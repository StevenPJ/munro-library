package com.stevenpj.infra

import com.stevenpj.domain.HillCategory

import com.stevenpj.domain.Munro
import com.stevenpj.domain.MunroCriteria
import com.stevenpj.domain.MunroRepository
import com.stevenpj.domain.SortOrder
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
        result == matched

        where:
        category               | matched
        HillCategory.MUNRO     | [MUNROE]
        HillCategory.MUNRO_TOP | [MUNROE_TOP]
        HillCategory.EITHER    | [MUNROE, MUNROE_TOP]
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

    def "should filter by minimum height"() {
        given:
        repository.save(NON_BLANK_MUNROE.heightInMeters(1).build())
        repository.save(NON_BLANK_MUNROE.heightInMeters(5).build())

        when:
        def result = repository.findAll(munroCriteria().minHeight(minHeight).build())

        then:
        result.collect { it.heightInMeters } == matched

        where:
        minHeight | matched
        0         | [1, 5]
        1         | [1, 5]
        5         | [5]
        6         | []
    }

    def "should filter by maximum height"() {
        given:
        repository.save(NON_BLANK_MUNROE.heightInMeters(1).build())
        repository.save(NON_BLANK_MUNROE.heightInMeters(5).build())

        when:
        def result = repository.findAll(munroCriteria().maxHeight(maxHeight).build())

        then:
        result.collect { it.heightInMeters } == matched

        where:
        maxHeight | matched
        0         | []
        1         | [1]
        5         | [1, 5]
        6         | [1, 5]
    }

    def "should sort by heightInMeters"() {
        given:
        repository.save(SMALL_MUNRO)
        repository.save(LARGE_MUNRO)

        when:
        def result = repository.findAll(munroCriteria()
                .sort("heightInMeters")
                .sortOrder(sortOrder)
                .build())

        then:
        result == expectedOrder

        where:
        sortOrder            | expectedOrder
        SortOrder.DESCENDING | [LARGE_MUNRO, SMALL_MUNRO]
        SortOrder.ASCENDING  | [SMALL_MUNRO, LARGE_MUNRO]
    }

    def "should sort by name"() {
        given:
        repository.save(BEN_LOMOND)
        repository.save(CRUACH_ARDRAIN)

        when:
        def result = repository.findAll(munroCriteria()
                .sort("name")
                .sortOrder(sortOrder)
                .build())

        then:
        result == expectedOrder

        where:
        sortOrder            | expectedOrder
        SortOrder.DESCENDING | [CRUACH_ARDRAIN, BEN_LOMOND]
        SortOrder.ASCENDING  | [BEN_LOMOND, CRUACH_ARDRAIN]
    }

    static final Munro.MunroBuilder NON_BLANK_MUNROE = Munro.builder().hillCategory("MUN")
    static final Munro MUNROE = Munro.builder().hillCategory("MUN").build()
    static final Munro MUNROE_TOP = Munro.builder().hillCategory("TOP").build()

    static final Munro LARGE_MUNRO = NON_BLANK_MUNROE.heightInMeters(999).build()
    static final Munro SMALL_MUNRO = NON_BLANK_MUNROE.heightInMeters(1).build()

    static final Munro BEN_LOMOND = NON_BLANK_MUNROE.name("Ben Lomond").build()
    static final Munro CRUACH_ARDRAIN = NON_BLANK_MUNROE.name("Cruach Ardrain").build()

}
