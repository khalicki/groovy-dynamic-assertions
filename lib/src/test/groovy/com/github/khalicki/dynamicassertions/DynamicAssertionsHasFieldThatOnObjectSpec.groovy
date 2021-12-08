package com.github.khalicki.dynamicassertions


import com.github.khalicki.dynamicassertions.data.ObjectWithObjectField
import com.github.khalicki.dynamicassertions.data.ObjectWithTitleField
import com.github.khalicki.dynamicassertions.data.ObjectWithTwoObjectField
import spock.lang.Specification

class DynamicAssertionsHasFieldThatOnObjectSpec extends Specification {

    def "should succeed when hasFieldThat is called on not null object field"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithObjectField(new ObjectWithTitleField("Rebel Without a Cause")))
                .hasMovieThat()
    }

    def "should fail when field does not exist"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithObjectField(new ObjectWithTitleField("Rebel Without a Cause")))
                .hasCinemaThat()

        then:
            thrown(MissingPropertyException)
    }

    def "should fail when object is null"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithObjectField(null))
                .hasMovieThat()

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('field != null')
    }

    def "should succeed when calling nested assertion"() {
        expect:
            DynamicAssertions.assertThat(new ObjectWithObjectField(new ObjectWithTitleField("Rebel Without a Cause")))
                .hasMovieThat()
                    .hasTitle("Rebel Without a Cause")
    }

    def "should fail when nested assertion fails"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithObjectField(new ObjectWithTitleField("Rebel Without a Cause")))
                .hasMovieThat()
                    .hasTitle("Matrix")

        then:
            def exception = thrown(AssertionError)
            exception.message.contains('actual == expected')
            exception.message.contains('Rebel Without a Cause')
            exception.message.contains('Matrix')
    }

    def "should succeed when asserting two nested fields separately"() {
        when:
            def twoMovies = new ObjectWithTwoObjectField(
                new ObjectWithTitleField("Seven"),
                new ObjectWithTitleField("Fight club")
            )

        then:
            DynamicAssertions.assertThat(twoMovies)
                .hasFirstMovieThat()
                    .hasTitle("Seven")

        and:
            DynamicAssertions.assertThat(twoMovies)
                .hasSecondMovieThat()
                    .hasTitle("Fight club")
    }

    def "should succeed when asserting two nested fields joined by and()"() {
        when:
            def twoMovies = new ObjectWithTwoObjectField(
                new ObjectWithTitleField("Seven"),
                new ObjectWithTitleField("Fight club")
            )

        then:
            // formatter:off
            DynamicAssertions.assertThat(twoMovies)
                .hasFirstMovieThat()
                    .hasTitle("Seven")
                .and()
                .hasSecondMovieThat()
                    .hasTitle("Fight club")
            // formatter:on
    }

    def "should throw NoParentAssertion when and() is called on root assertion"() {
        when:
            DynamicAssertions.assertThat(new ObjectWithObjectField(new ObjectWithTitleField("Rebel Without a Cause")))
                .and()

        then:
            def exception = thrown(NoParentAssertion)
    }


}
