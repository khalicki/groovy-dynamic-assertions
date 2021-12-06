package com.github.khalicki.dynamicassertions

class DynamicAssertions implements GroovyInterceptable, AssertionNode {
    public static final String AS_BOOLEAN_METHOD = "asBoolean"
    public static final String HAS_ASSERTION_PREFIX = "has"
    public static final String HAS_EMPTY_ASSERTION_PREFIX = "hasEmpty"
    public static final String THAT_ASSERTION_POSTFIX = "That"

    private Object objectUnderTest

    DynamicAssertions(Object objectUnderTest) {
        this.objectUnderTest = objectUnderTest
    }

    static assertThat(Object objectUnderTest) {
        assert objectUnderTest != null
        return new DynamicAssertions(objectUnderTest)
    }

    @Override
    Object invokeMethod(String assertionName, Object args) {
        if (assertionName == AS_BOOLEAN_METHOD) return true
        Object[] argumentList = (Object[]) args
        if (assertionName.startsWith(HAS_ASSERTION_PREFIX) && assertionName.endsWith(THAT_ASSERTION_POSTFIX)) {
            def fieldName = extractFieldName(assertionName, HAS_ASSERTION_PREFIX, THAT_ASSERTION_POSTFIX)
            return assertHasFieldThat(this, objectUnderTest, fieldName, assertionName)
        } else if (assertionName.startsWith(HAS_EMPTY_ASSERTION_PREFIX)) {
            def fieldName = extractFieldName(assertionName, HAS_EMPTY_ASSERTION_PREFIX)
            return assertEmptyField(this, objectUnderTest, fieldName, assertionName)
        } else if (assertionName.startsWith(HAS_ASSERTION_PREFIX)) {
            if (argumentList == null || argumentList.length < 1) throw new IllegalArgumentException("Missing expected value in assertion ${assertionName}()")
            def fieldName = extractFieldName(assertionName, HAS_ASSERTION_PREFIX)
            def expected = argumentList[0]
            return assertField(this, objectUnderTest, fieldName, expected)
        } else {
            throw new MissingMethodException(assertionName, DynamicAssertions.class, args)
        }
    }

    static AssertionNode assertHasFieldThat(DynamicAssertions assertionObject, Object objectUnderTest, String fieldName, String assertionName) {
        def field = objectUnderTest[fieldName]
        assert field != null
        if (field instanceof List) {
            def list = (List) field
            return assertListField(assertionObject, list)
        } else if (field instanceof Object) {
            def object = (Object) field
            return assertObjectField(assertionObject, object)
        } else {
            throw new UnsupportedAssertion(assertionName)
        }
    }

    static DynamicAssertions assertField(DynamicAssertions assertionObject, Object objectUnderTest, String fieldName, Object expected) {
        def actual = objectUnderTest[fieldName]
        assert actual == expected
        return assertionObject
    }

    static DynamicListAssertions assertListField(DynamicAssertions assertionObject, List list) {
        assert list != null
        return new DynamicListAssertions(list, assertionObject)
    }

    static DynamicAssertions assertObjectField(DynamicAssertions assertionObject, Object object) {
        assert object != null
        return new DynamicAssertions(object)
    }

    static DynamicAssertions assertEmptyField(DynamicAssertions assertionObject, Object objectUnderTest, String fieldName, String assertionName) {
        def field = objectUnderTest[fieldName]
        assert field != null
        if (field instanceof List) {
            def list = (List) field
            assert list.isEmpty()
        } else if (field instanceof String) {
            assert field.isEmpty()
        } else {
            throw new UnsupportedAssertion(assertionName)
        }
        return assertionObject
    }

    static String extractFieldName(String methodName, String assertionPrefix, String assertionPostfix = null) {
        if (!methodName.startsWith(assertionPrefix)) return methodName
        if (methodName == assertionPrefix) throw new MissingFieldNameInAssertion(methodName)
        def firstLetter = methodName.charAt(assertionPrefix.length()).toLowerCase().toString()
        def tail = methodName.substring(assertionPrefix.length() + 1)
        if (assertionPostfix != null && tail.endsWith(assertionPostfix)) {
            tail = tail.substring(0, tail.length() - assertionPostfix.length())
        }
        return firstLetter + tail
    }
}

class MissingFieldNameInAssertion extends RuntimeException {
    MissingFieldNameInAssertion(String methodName) {
        super("Assertion with name '$methodName' should contain field name")
    }
}

class UnsupportedAssertion extends RuntimeException {
    UnsupportedAssertion(String methodName) {
        super("Assertion with name '$methodName' is not supported on given type")
    }
}