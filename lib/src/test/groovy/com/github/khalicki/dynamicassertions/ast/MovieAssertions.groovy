package com.github.khalicki.dynamicassertions.ast

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Assertions
@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class MovieAssertions {
    String title

    MovieAssertions(String title) {
        this.title = title
    }


//    @AssertNotNullArgument
//    void hasTitle(String expected) {
//        println 12
//        assert 1 == 1
//    }
}
