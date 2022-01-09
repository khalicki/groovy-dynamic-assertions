package com.github.khalicki.dynamicassertions

import com.github.khalicki.dynamicassertions.data.EmptyObject
import com.github.khalicki.dynamicassertions.data.ObjectWithEqualsMethod
import com.github.khalicki.dynamicassertions.data.ObjectWithIntField
import spock.lang.Specification


class DynamicAssertionsIsEqualToSpec extends Specification {

    def "should succeed when primitive values are equal"() {
        expect:
            DynamicAssertions.assertThat(5).isEqualTo(5)
    }

    def "should fail when primitive values are not equal"() {
        when:
            DynamicAssertions.assertThat(5).isEqualTo(6)

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('objectUnderTest == expected')
    }

    def "should fail when expected value is missing"() {
        when:
            DynamicAssertions.assertThat(5).isEqualTo()

        then:
            def exception = thrown(IllegalArgumentException)
            exception.message.contains('Missing expected value in assertion isEqualTo()')
    }

    def "should succeed when isEqualTo is called on same object"() {
        given:
            def object = new EmptyObject()

        expect:
            DynamicAssertions.assertThat(object).isEqualTo(object)
    }

    def "should fail when called on object of different type"() {
        when:
            DynamicAssertions.assertThat(new EmptyObject()).isEqualTo(new ObjectWithIntField(5))

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('objectUnderTest == expected')
    }

    def "should fail when called different obejct reference without overridden equals method"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithIntField(5)).isEqualTo(new ObjectWithIntField(5))

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('objectUnderTest == expected')
    }

    def "should succeed when equals returns true"() {
        given:
            def object1 = new ObjectWithEqualsMethod(5)
            def object2 = new ObjectWithEqualsMethod(5)

        expect:
            DynamicAssertions.assertThat(object1).isEqualTo(object2)
    }

    def "should succeed when equals returns false"() {
        given:
            def object1 = new ObjectWithEqualsMethod(5)
            def object2 = new ObjectWithEqualsMethod(6)

        when:
            DynamicAssertions.assertThat(object1).isEqualTo(object2)

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('objectUnderTest == expected')
    }
}