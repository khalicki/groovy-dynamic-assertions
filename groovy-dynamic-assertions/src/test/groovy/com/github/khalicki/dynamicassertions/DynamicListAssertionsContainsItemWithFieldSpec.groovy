package com.github.khalicki.dynamicassertions

import com.github.khalicki.dynamicassertions.data.ObjectWithIntField
import com.github.khalicki.dynamicassertions.data.ObjectWithTitleField
import spock.lang.Specification

class DynamicListAssertionsContainsItemWithFieldSpec extends Specification {

    def "should fail when called on empty list"() {
        when:
            DynamicListAssertions.assertThat([]).containsItemWithTitle('When Harry met Sally')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.any { it[propertyName] == expected }')
    }

    def "should succeed when list contains given element"() {
        given:
            def list = [
                    new ObjectWithTitleField("When Harry met Sally")
            ]

        expect:
            DynamicListAssertions.assertThat(list).containsItemWithTitle('When Harry met Sally')
    }

    def "should fail when list does not contain element with expected field"() {
        given:
            def list = [
                    new ObjectWithTitleField("Dune")
            ]

        when:
            DynamicListAssertions.assertThat([]).containsItemWithTitle('When Harry met Sally')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.any { it[propertyName] == expected }')
    }

    def "should succeed when list contains given element with primitive type"() {
        given:
            def list = [
                    new ObjectWithIntField(41)
            ]

        expect:
            DynamicListAssertions.assertThat(list).containsItemWithNumber(41)
    }

    def "should fail when list item does not have expected property"() {
        given:
            def list = [
                    new ObjectWithIntField(12)
            ]

        when:
            DynamicListAssertions.assertThat(list).containsItemWithTitle('When Harry met Sally')

        then:
            def exception = thrown(MissingPropertyException)
            exception.message.contains('No such property: title for class')
    }

    def "should succeed when list is asserted in different order than in list"() {
        given:
            def list = [
                    new ObjectWithTitleField("When Harry met Sally"),
                    new ObjectWithTitleField("Dune")
            ]

        expect:
            DynamicListAssertions.assertThat(list)
                    .containsItemWithTitle('Dune')
                    .containsItemWithTitle('When Harry met Sally')
    }
}