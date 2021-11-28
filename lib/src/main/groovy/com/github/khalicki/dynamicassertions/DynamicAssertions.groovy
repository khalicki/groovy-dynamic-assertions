package com.github.khalicki.dynamicassertions

class DynamicAssertions implements GroovyInterceptable {
    public static final String AS_BOOLEAN_METHOD = "asBoolean"
    public static final String HAS_ASSERTION_PREFIX = "has"

    private Object objectUnderTest

    DynamicAssertions(Object objectUnderTest) {
        this.objectUnderTest = objectUnderTest
    }

    static assertThat(Object objectUnderTest) {
        assert objectUnderTest != null
        return new DynamicAssertions(objectUnderTest)
    }

    @Override
    Object invokeMethod(String name, Object args) {
        if (name == AS_BOOLEAN_METHOD) return true
        if (name.startsWith(HAS_ASSERTION_PREFIX)) {
            Object[] argumentList = (Object[]) args
            if (argumentList == null || argumentList.length < 1) throw new IllegalArgumentException("Missing expected value in assertion ${name}()")
            def fieldName = extractFieldName(name, HAS_ASSERTION_PREFIX)
            def expected = argumentList[0]
            return assertField(this, objectUnderTest, fieldName, expected)
        } else {
            throw new MissingMethodException(name, DynamicAssertions.class, args)
        }
    }

    static DynamicAssertions assertField(DynamicAssertions assertionObject, Object objectUnderTest, String fieldName, Object expected) {
        def actual = objectUnderTest[fieldName]
        assert actual == expected
        return assertionObject
    }

    static String extractFieldName(String methodName, String assertionPrefix) {
        if (!methodName.startsWith(assertionPrefix)) return methodName
        if (methodName == assertionPrefix) throw new MissingFieldNameInAssertion(methodName)
        def firstLetter = methodName.charAt(assertionPrefix.length()).toLowerCase().toString()
        def tail = methodName.substring(assertionPrefix.length() + 1)
        return firstLetter + tail
    }
}

class MissingFieldNameInAssertion extends RuntimeException {

    MissingFieldNameInAssertion(String methodName) {
        super("Assertion with name '$methodName' should contain field name")
    }
}