package com.github.khalicki.dynamicassertions


import com.github.khalicki.dynamicassertions.data.ObjectWithTwoFields
import spock.lang.Specification

class DynamicListAssertionsContainsItemWithFieldThatSpec extends Specification {

    def "should fail when called on empty list"() {
        when:
            DynamicListAssertions.assertThat([]).containsItemWithTitleThat('When Harry met Sally')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.any { it[propertyName] == expected }')
    }

    def "should fail when called on null list"() {
        when:
            DynamicListAssertions.assertThat(null).containsItemWithTitleThat('When Harry met Sally')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.any { it[propertyName] == expected }')
    }

    def "should succeed when list contains given element"() {
        given:
            def list = [
                new ObjectWithTwoFields("When Harry met Sally", "Rob Reiner")
            ]

        expect:
            DynamicListAssertions.assertThat(list).containsItemWithTitleThat('When Harry met Sally')
    }

    def "should fail when called list that doesn't contain matching element"() {
        given:
            def list = [
                new ObjectWithTwoFields("When Harry met Sally", "Rob Reiner")
            ]

        when:
            DynamicListAssertions.assertThat([]).containsItemWithTitleThat('Breaveheart')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.any { it[propertyName] == expected }')
    }

    def "should succeed when chained assertion passes"() {
        given:
            def list = [
                new ObjectWithTwoFields("When Harry met Sally", "Rob Reiner")
            ]

        expect:
            // @formatter:off
            DynamicListAssertions.assertThat(list)
                .containsItemWithTitleThat('When Harry met Sally')
                    .hasDirector('Rob Reiner')
            // @formatter:on
    }

    def "should fail when chained assertion fails"() {
        given:
            def list = [
                new ObjectWithTwoFields("When Harry met Sally", "Rob Reiner")
            ]

        when:
            // @formatter:off
            DynamicListAssertions.assertThat(list)
                .containsItemWithTitleThat('When Harry met Sally')
                    .hasDirector('Billy Cristal')
            // @formatter:on

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('actual == expected')
            exception.message.contains('Billy Cristal')
    }

    def "should succeed when assertions are chained using and method"() {
        given:
            def list = [
                new ObjectWithTwoFields("When Harry met Sally", "Rob Reiner"),
                new ObjectWithTwoFields("Lobster", "Yorgos Lanthimos")
            ]

        expect:
            // @formatter:off
            DynamicListAssertions.assertThat(list)
                .containsItemWithTitleThat('When Harry met Sally')
                    .hasDirector('Rob Reiner')
                    .and()
                .containsItemWithTitleThat('Lobster')
                    .hasDirector("Yorgos Lanthimos")
            // @formatter:on
    }
}
