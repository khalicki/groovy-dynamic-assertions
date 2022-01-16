package com.github.khalicki.dynamicassertions


import spock.lang.Specification

class DynamicListAssertionsAssertThatSpec extends Specification {

    def "should succeed when null is asserted"() {
        expect:
            DynamicListAssertions.assertThat(null)
    }

    def "should succeed when list is empty"() {
        expect:
            DynamicListAssertions.assertThat([])
    }

    def "should fail when isNotNull() is called on null list"() {
        when:
            DynamicListAssertions.assertThat(null).isNotNull()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('objectUnderTest != null')
    }

    def "should succeed when isNotNull() is called on not null list"() {
        expect:
            DynamicListAssertions.assertThat([]).isNotNull()
    }

    def "should fail when isNull() is called on not null list"() {
        when:
            DynamicListAssertions.assertThat([]).isNull()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('objectUnderTest == null')
    }

    def "should succeed when isNull() is called on null list"() {
        expect:
            DynamicListAssertions.assertThat(null).isNull()
    }
}
