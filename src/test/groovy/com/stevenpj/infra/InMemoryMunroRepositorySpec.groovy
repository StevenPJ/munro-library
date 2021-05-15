package com.stevenpj.infra

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

}
