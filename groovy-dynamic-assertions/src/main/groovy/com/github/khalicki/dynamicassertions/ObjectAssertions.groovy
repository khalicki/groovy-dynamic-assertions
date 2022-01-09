package com.github.khalicki.dynamicassertions

class ObjectAssertions {

    static DynamicAssertions isNotNull(DynamicAssertions assertionObject, Object objectUnderTest) {
        assert objectUnderTest != null
        return assertionObject
    }

    static DynamicAssertions isNull(DynamicAssertions assertionObject, Object objectUnderTest) {
        assert objectUnderTest == null
        return assertionObject
    }

    static DynamicAssertions isEqualTo(DynamicAssertions assertionObject, Object objectUnderTest, Object expected) {
        assert objectUnderTest == expected
        return assertionObject
    }
}
