package com.github.khalicki.dynamicassertions

import com.github.khalicki.dynamicassertions.data.ObjectWithCamelCaseField
import com.github.khalicki.dynamicassertions.data.ObjectWithOneLetterField
import com.github.khalicki.dynamicassertions.data.ObjectWithTitleField
import com.github.khalicki.dynamicassertions.data.ObjectWithTwoFields
import spock.lang.Specification

class DynamicAssertionsHasFieldSpec extends Specification {

    def "should fail when field does not exist"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField())
                .hasName('Some value')

        then:
            thrown(MissingPropertyException)
    }

    def "should succeed when field exists and expected value matches"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithTitleField('When Harry met Sally'))
                .hasTitle('When Harry met Sally')
    }

    def "should fail when field exists not exist but value does not match"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField('When Harry met Sally'))
                .hasTitle('Some value')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('When Harry met Sally')
            exception.message.contains('Some value')
    }

    def "should succeed when field consists of many words"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithCamelCaseField('Blade Runner'))
                .hasVeryLongFieldName('Blade Runner')
    }

    def "should fail when first assertion fails"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTwoFields('When Harry met Sally', 'Rob Reiner'))
                .hasTitle('Blade Runner')
                .hasDirector('Rob Reiner')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('When Harry met Sally')
            !exception.message.contains('Rob Reiner')
    }

    def "should fail when second assertion fails"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTwoFields('When Harry met Sally', 'Rob Reiner'))
                .hasTitle('When Harry met Sally')
                .hasDirector('Ridley Scott')

        then:
            def exception = thrown(AssertionError)
            !exception.message.contains('When Harry met Sally')
            exception.message.contains('Ridley Scott')
    }

    def "should succeed when all assertion matches"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithTwoFields('When Harry met Sally', 'Rob Reiner'))
                .hasTitle('When Harry met Sally')
                .hasDirector('Rob Reiner')
    }

    def "should fail when no field name is given"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField('When Harry met Sally'))
                .has('Some value')

        then:
            thrown(MissingFieldNameInAssertion)
    }

    def "should succeed with one letter field"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithOneLetterField(7))
                .hasN(7)
    }

    def "should fail when expected value is not given"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField('When Harry met Sally'))
                .hasTitle()

        then:
            def exception = thrown(IllegalArgumentException)
            exception.message == "Missing expected value in assertion hasTitle()"
    }
}
