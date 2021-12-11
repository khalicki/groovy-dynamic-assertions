package com.github.khalicki.dynamicassertions.demo


import java.time.Duration
import spock.lang.Specification
import spock.lang.Subject

import static com.github.khalicki.dynamicassertions.DynamicAssertions.assertThat

class MovieDatabaseFacadeSpec extends Specification {

    @Subject
    MovieDatabaseFacade facade

    def setup() {
        facade = new MovieDatabaseFacade()
    }

    def "findById should return movie with title"() {
        when:
            def movie = facade.findById(1)

        then:
            assertThat(movie)
                    .hasTitle("When Harry Met Sally")
    }

    def "findById should return movie with year and duration"() {
        when:
            def movie = facade.findById(1)

        then:
            assertThat(movie)
                    .hasReleaseYear(1989)
                    .hasRunningTime(Duration.parse("PT1H35M"))
    }
}
