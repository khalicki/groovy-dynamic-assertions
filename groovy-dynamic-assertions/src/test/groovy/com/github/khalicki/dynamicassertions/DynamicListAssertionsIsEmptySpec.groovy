package com.github.khalicki.dynamicassertions

import spock.lang.Specification


class DynamicListAssertionsIsEmptySpec extends Specification {

    def "should fail when isEmpty() is called on not empty list"() {
        when:
            DynamicListAssertions.assertThat([42]).isEmpty()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.isEmpty()')
    }

    def "should succeed when isEmpty() is called on empty list"() {
        expect:
            DynamicListAssertions.assertThat([]).isEmpty()
    }

    def "should fail when isEmpty() is called on null list"() {
        when:
            DynamicListAssertions.assertThat(null).isEmpty()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest != null')
    }

    def "should fail when isNotEmpty() is called on empty list"() {
        when:
            DynamicListAssertions.assertThat([]).isNotEmpty()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('!listUnderTest.isEmpty()')
    }

    def "should succeed when isNotEmpty() is called on not empty list"() {
        expect:
            DynamicListAssertions.assertThat([42]).isNotEmpty()
    }

    def "should fail when isNotEmpty() is called on null list"() {
        when:
            DynamicListAssertions.assertThat(null).isNotEmpty()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest != null')
    }
}