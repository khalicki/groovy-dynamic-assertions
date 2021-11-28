package com.github.khalicki.dynamicassertions

import com.github.khalicki.dynamicassertions.data.ObjectWithListField
import com.github.khalicki.dynamicassertions.data.ObjectWithTitleField
import spock.lang.Specification

class DynamicAssertionsHasFieldThatOnIterableSpec extends Specification {

    def "should succeed when hasFieldThat is called on empty list"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField([]))
                .hasIngredientsThat()
    }

    def "should fail when field does not exist"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField([]))
                .hasInstructionsThat()

        then:
            thrown(MissingPropertyException)
    }

    def "should fail when list is null"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField(null))
                .hasIngredientsThat()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('list != null')
    }

    def "should fail when field is not list"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField('Matrix'))
                .hasTitleThat()

        then:
            thrown(AssertionError)
    }

    def "should succeed when list contains given element"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami', 'cheese']))
                .hasIngredientsThat()
                    .contains('cheese')
    }

    def "should succeed when list contains multiple element"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami', 'cheese']))
                .hasIngredientsThat()
                    .contains('cheese')
                    .contains('salami')
    }

    def "should fail when list does not contain element"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami', 'cheese']))
                .hasIngredientsThat()
                    .contains('cheese')
                    .contains('bacon')

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.contains(expected)')
            exception.message.contains('bacon')
    }

    def "should succeed when isEmpty is called on empty list"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField([]))
                .hasIngredientsThat()
                    .isEmpty()
    }

    def "should fail when isEmpty is called on not empty list"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami']))
                .hasIngredientsThat()
                    .isEmpty()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.isEmpty()')
    }
}
