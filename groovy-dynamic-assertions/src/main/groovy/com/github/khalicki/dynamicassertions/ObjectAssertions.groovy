package com.github.khalicki.dynamicassertions

class ObjectAssertions {

    public static final String IS_NULL_METHOD = "isNull"
    public static final String IS_NOT_NULL_METHOD = "isNotNull"

    static AssertionNode isNotNull(AssertionNode assertionObject, Object objectUnderTest) {
        assert objectUnderTest != null
        return assertionObject
    }

    static AssertionNode isNull(AssertionNode assertionObject, Object objectUnderTest) {
        assert objectUnderTest == null
        return assertionObject
    }

    static AssertionNode isEqualTo(AssertionNode assertionObject, Object objectUnderTest, Object expected) {
        assert objectUnderTest == expected
        return assertionObject
    }
}
