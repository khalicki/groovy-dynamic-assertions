package com.github.khalicki.dynamicassertions

import com.github.khalicki.dynamicassertions.data.ObjectWithIntField
import com.github.khalicki.dynamicassertions.data.ObjectWithListField
import com.github.khalicki.dynamicassertions.data.ObjectWithTitleField
import com.github.khalicki.dynamicassertions.data.Recipe
import spock.lang.Specification

class DynamicAssertionsHasFieldThatOnListSpec extends Specification {

    def "should succeed when hasFieldThat() is called on empty list"() {
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
            exception.message.contains('field != null')
    }

    def "should fail when field is number"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithIntField(42))
                .hasNumberThat()

        then:
            thrown(UnsupportedAssertion)
    }

    def "should fail when field is string"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithTitleField("Matrix"))
                .hasTitleThat()

        then:
            thrown(UnsupportedAssertion)
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

    def "should succeed when isEmpty() is called on empty list"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField([]))
                .hasIngredientsThat()
                    .isEmpty()
    }

    def "should fail when isEmpty() is called on not empty list"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami']))
                .hasIngredientsThat()
                    .isEmpty()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.isEmpty()')
    }

    def "should succeed when hasSize() is called on empty list"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField([]))
                .hasIngredientsThat()
                    .hasSize(0)
    }

    def "should succeed when hasSize() is called on not empty list"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithListField(['tomato']))
                .hasIngredientsThat()
                    .hasSize(1)
    }

    def "should fail when hasSize() is called on not empty list but has different size"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithListField(['salami']))
                .hasIngredientsThat()
                    .hasSize(2)

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('listUnderTest.size() == expectedSize')
    }

    def "should succeed when returning from list assertions with and()"() {
        given:
            def recipe = new Recipe("Cuba Libre",
                [
                    new Recipe.Ingredient("Rum"),
                    new Recipe.Ingredient("Coke")
                ]
            )

        expect:
            // formatter:off
            DynamicAssertions.assertThat(recipe)
                .hasIngredientsThat()
                    .hasSize(2)
                .and()
                .hasTitle("Cuba Libre")
            // formatter:on
    }
}
