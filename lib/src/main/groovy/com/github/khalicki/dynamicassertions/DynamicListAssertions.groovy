package com.github.khalicki.dynamicassertions

class DynamicListAssertions implements AssertionNode {
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

    DynamicListAssertions hasSize(int expectedSize) {
        assert listUnderTest.size() == expectedSize
        return this
    }
}
