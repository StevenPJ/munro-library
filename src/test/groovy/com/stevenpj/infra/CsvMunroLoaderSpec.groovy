package com.stevenpj.infra

import com.stevenpj.domain.Munro
import com.stevenpj.infra.csv.CsvLoadingException
import com.stevenpj.infra.csv.CsvMunroLoader
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class CsvMunroLoaderSpec extends Specification {

    CsvMunroLoader munroLoader = new CsvMunroLoader()

    def "should load no munro data from empty csv"() {
        given:
        def csvResource = new ClassPathResource("csv/empty.csv")

        when:
        def loadedMunros = munroLoader.loadFrom(csvResource)

        then:
        loadedMunros.isEmpty()
    }

    def "should load munro data from csv"() {
        given:
        def csvResource = new ClassPathResource("csv/single.csv")

        when:
        def loadedMunros = munroLoader.loadFrom(csvResource)

        then:
        loadedMunros == [
                Munro.builder()
                .name("Ben Chonzie")
                .heightInMeters(931)
                .hillCategory("MUN")
                .gridReference("NN773308")
                .build()
        ]
    }

    def "should ignore unrelated data in csv"() {
        given:
        def csvResource = new ClassPathResource("csv/csv_with_footer.csv")

        when:
        def loadedMunros = munroLoader.loadFrom(csvResource)

        then:
        loadedMunros == []
    }

    def "should only parse valid munro rows"() {
        given:
        def csvResource = new ClassPathResource("csv/simple.csv")

        when:
        def loadedMunros = munroLoader.loadFrom(csvResource)

        then:
        loadedMunros.collect{ it.name } == ["Ben Chonzie", "Ben Vorlich", "Ben Lomond"]
    }

    def "should allow missing category and grid references"() {
        given:
        def csvResource = new ClassPathResource("csv/missing_category_and_grid_ref.csv")

        when:
        def loadedMunros = munroLoader.loadFrom(csvResource)

        then:
        loadedMunros == [
                Munro.builder()
                        .name("Name")
                        .heightInMeters(1)
                        .gridReference('')
                        .hillCategory('')
                        .build()
        ]
    }

    def "should error when csv does not exist"() {
        given:
        def csvResource = new ClassPathResource("missing.file")

        when:
        munroLoader.loadFrom(csvResource)

        then:
        thrown CsvLoadingException
    }

}
