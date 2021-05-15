package com.stevenpj.web

import com.stevenpj.domain.HillCategory

import com.stevenpj.domain.Munro
import com.stevenpj.domain.MunroRepository
import com.stevenpj.domain.SortOrder
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.stevenpj.builder.MunroCriteriaBuilder.munroCriteria
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

    def "should allow the user to specify search criteria"() {
        when:
        mvc.perform(get("/munros")
                .param("hillCategory", HillCategory.MUNRO.name())
                .param("limit", "2")
                .param("minHeight", "10")
                .param("maxHeight", "20")
                .param("sort", "heightInMeters")
                .param("sortOrder", "DESCENDING")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        then:
        1 * munroRepository.findAll(munroCriteria()
                .with(HillCategory.MUNRO)
                .limit(2)
                .minHeight(10)
                .maxHeight(20)
                .sort("heightInMeters")
                .sortOrder(SortOrder.DESCENDING)
                .build())
    }

    def "should filter by either hill category by default"() {
        when:
        mvc.perform(get("/munros")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        then:
        1 * munroRepository.findAll(munroCriteria().with(HillCategory.EITHER).build())
    }

    def "should return error when filtering by invalid category"() {
        expect:
        mvc.perform(get("/munros")
                .param("hillCategory", "INVALID_CATEGORY")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
    }

    def "should return error when limit is negative"() {
        expect:
        mvc.perform(get("/munros")
                .param("limit", "-1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
    }

    def "should return error when maxHeight is less than minHeight"() {
        expect:
        mvc.perform(get("/munros")
                .param("minHeight", "5")
                .param("maxHeight", "1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
    }
}