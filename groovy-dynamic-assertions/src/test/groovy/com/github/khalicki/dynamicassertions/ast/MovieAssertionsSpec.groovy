package com.github.khalicki.dynamicassertions.ast

import spock.lang.Specification

class MovieAssertionsSpec extends Specification {

    def "should generate hasField assertion"() {
        when:
            def movieAssertions = new MovieAssertions("Matrix")

        then:
            movieAssertions.hasTitle("Matrix")
    }
}
