package com.github.khalicki.dynamicassertions

import com.github.khalicki.dynamicassertions.data.ObjectWithIntField
import com.github.khalicki.dynamicassertions.data.ObjectWithListField
import com.github.khalicki.dynamicassertions.data.ObjectWithTitleField
import spock.lang.Specification

class DynamicAssertionsHasEmptyFieldSpec extends Specification {

    def "should succeed when hasEmptyField() is called on empty list"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField([]))
                .hasEmptyIngredients()
    }

    def "should fail when hasEmptyField() is called on not empty list"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami']))
                .hasEmptyIngredients()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('list.isEmpty()')
    }

    def "should succeed when hasEmptyField() is called on empty String"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithTitleField(""))
                .hasEmptyTitle()
    }

    def "should fail when hasEmptyField() is called on not empty String"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField("When Harry met Sally"))
                .hasEmptyTitle()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('field.isEmpty()')
    }

    def "should fail when hasEmptyField() is called on null field"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField(null))
                .hasEmptyTitle()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('field != null')
    }

    def "should fail when hasEmptyField() is called on int field"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithIntField(6))
                .hasEmptyNumber()

        then:
            def exception = thrown(UnsupportedAssertion)
            exception.message.contains('Assertion with name \'hasEmptyNumber\' is not supported on given type')
    }
}
