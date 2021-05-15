package com.stevenpj.infra

import com.stevenpj.domain.HillCategory

import com.stevenpj.domain.Munro
import com.stevenpj.domain.MunroRepository
import spock.lang.Specification

class InMemoryMunroRepositorySpec extends Specification {

    MunroRepository repository = new InMemoryMunroRepository()

    def "should return all munros"() {
        given:
        def munro = Munro.builder().name("munro").build()
        repository.save(munro)

        expect:
        repository.findAll().contains(munro)
    }

    def "should filter by hill category"() {
        given:
        def munro = Munro.builder().hillCategory("MUN").build()
        def munroTop = Munro.builder().hillCategory("TOP").build()
        repository.save(munro)
        repository.save(munroTop)

        when:
        def result = repository.findAll(category)

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
        def munro = Munro.builder().hillCategory("MUN").build()
        def blank = Munro.builder().build()
        repository.save(munro)
        repository.save(blank)

        when:
        def result = repository.findAll(HillCategory.EITHER)

        then:
        !result.contains(blank)
    }
}
