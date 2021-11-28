package com.github.khalicki.dynamicassertions

class DynamicListAssertions {
    private List listUnderTest
    private DynamicAssertions parent

    DynamicListAssertions(List listUnderTest, DynamicAssertions parent) {
        this.listUnderTest = listUnderTest
        this.parent = parent
    }

    DynamicListAssertions contains(Object expected) {
        assert listUnderTest.contains(expected)
        return this
    }

    DynamicListAssertions isEmpty(Object expected) {
        assert listUnderTest.isEmpty()
        return this
    }
}
