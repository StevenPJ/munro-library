package com.stevenpj.acceptance

import com.stevenpj.domain.HillCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["munro_data=data/munrotab_v6.2.csv"])
@AutoConfigureMockMvc
class AcceptanceSpecIT extends Specification {

    @Autowired
    MockMvc mvc

    def "should read munro data on start and allow the user to query"() {
        expect:
        mvc.perform(get("/munros")
                .param("hillCategory", HillCategory.EITHER.name())
                .param("limit", "3")
                .param("minHeight", "1021")
                .param("maxHeight", "1023")
                .param("sort", "heightInMeters")
                .param("sortOrder", "DESCENDING")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.[*]', hasSize(3)))
                .andExpect(jsonPath('$.[0].name', is('Ben Avon - West Meur Gorm Craig')))
                .andExpect(jsonPath('$.[1].name', is('Liathach - Mullach an Rathain')))
                .andExpect(jsonPath('$.[2].name', is('Buachaille Etive Mor - Stob Dearg')))
    }

}
