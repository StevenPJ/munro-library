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
        def other = Munro.builder().hillCategory("OTHER").build()
        repository.save(munro)
        repository.save(other)

        when:
        def result = repository.findAll(HillCategory.MUNRO)

        then:
        result.contains(munro)
        !result.contains(other)
    }
}
