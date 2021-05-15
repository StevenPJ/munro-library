package com.stevenpj.web

import com.stevenpj.domain.HillCategory
import com.stevenpj.domain.HillCategoryCriteria
import com.stevenpj.domain.LimitCriteria
import com.stevenpj.domain.Munro
import com.stevenpj.domain.MunroRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class MunroControllerSpecIT extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    MunroRepository munroRepository = Mock()

    def "should return all munros"() {
        given:
        1 * munroRepository.findAll(_) >> [
                Munro.builder()
                    .name('Ben Chonzie')
                    .heightInMeters(931)
                    .hillCategory('MUN')
                    .gridReference('NN773308')
                    .build()
        ]
        expect:
        mvc.perform(get("/munros")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.[0].name', is('Ben Chonzie')))
                .andExpect(jsonPath('$.[0].heightInMeters', is(931)))
                .andExpect(jsonPath('$.[0].hillCategory', is('MUN')))
                .andExpect(jsonPath('$.[0].gridReference', is('NN773308')))
    }

    def "should filter by hill category"() {
        when:
        mvc.perform(get("/munros")
                .param("hillCategory", HillCategory.MUNRO.name())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        then:
        1 * munroRepository.findAll(new HillCategoryCriteria(HillCategory.MUNRO))
    }

    def "should filter by either hill category by default"() {
        when:
        mvc.perform(get("/munros")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        then:
        1 * munroRepository.findAll(new HillCategoryCriteria(HillCategory.EITHER))
    }

    def "should return error when filtering by invalid category"() {
        expect:
        mvc.perform(get("/munros")
                .param("hillCategory", "INVALID_CATEGORY")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
    }

    def "should limit number of results returned"() {
        when:
        mvc.perform(get("/munros")
                .param("limit", "10")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        then:
        1 * munroRepository.findAll(new LimitCriteria(10))
    }
}