package com.stevenpj;

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationSpecIT extends Specification {
    def "application starts"() {
        when: "the application starts up"
        then: "there are no errors"
        noExceptionThrown()
    }
}
