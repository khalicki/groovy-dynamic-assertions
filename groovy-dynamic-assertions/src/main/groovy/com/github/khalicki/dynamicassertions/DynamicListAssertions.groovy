package com.github.khalicki.dynamicassertions

/**
 * Class implements dynamic assertions on list of object
 *
 * Use static method assertThat() to start building assertions in tests
 * @author kamil.halicki
 */
class DynamicListAssertions implements AssertionNode {
    private List listUnderTest
    private AssertionNode parentAssertion

    DynamicListAssertions(List listUnderTest, AssertionNode parent) {
        this.listUnderTest = listUnderTest
        this.parentAssertion = parent
    }

    AssertionNode and() {
        if (parentAssertion != null)
            return parentAssertion
        else
            throw new NoParentAssertion(this)
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
