package com.github.khalicki.dynamicassertions

/**
 * Class implements dynamic assertions on single object
 *
 * Use static method assertThat() to start building assertions in tests
 */
class DynamicAssertions implements GroovyInterceptable, AssertionNode {
    public static final String AS_BOOLEAN_METHOD = "asBoolean"
    public static final String IS_EQUAL_TO_METHOD = "isEqualTo"
    public static final String HAS_ASSERTION_PREFIX = "has"
    public static final String HAS_EMPTY_ASSERTION_PREFIX = "hasEmpty"
    public static final String THAT_ASSERTION_POSTFIX = "That"

    private Object objectUnderTest
    private AssertionNode parentAssertion

    DynamicAssertions(Object objectUnderTest) {
        this.objectUnderTest = objectUnderTest
        this.parentAssertion = null
    }

    DynamicAssertions(Object objectUnderTest, AssertionNode parentAssertion) {
        this.objectUnderTest = objectUnderTest
        this.parentAssertion = parentAssertion
    }

    /**
     * Starting point of all dynamic assertions on single object
     *
     * @param objectUnderTest object against which all assertions will be executed
     * @return Assertions object that can evaluate dynamic assertions on object under test
     */
    static assertThat(Object objectUnderTest) {
        return new DynamicAssertions(objectUnderTest)
    }

    @Override
    Object invokeMethod(String assertionName, Object args) {
        if (assertionName == AS_BOOLEAN_METHOD) return true
        if (assertionName == AssertionNodeAssertions.AND_METHOD) return AssertionNodeAssertions.and(this, parentAssertion)
        if (assertionName == ObjectAssertions.IS_NOT_NULL_METHOD) return ObjectAssertions.isNotNull(this, objectUnderTest)
        if (assertionName == ObjectAssertions.IS_NULL_METHOD) return ObjectAssertions.isNull(this, objectUnderTest)
        Object[] argumentList = (Object[]) args
        if (assertionName == IS_EQUAL_TO_METHOD) {
            if (argumentList == null || argumentList.length < 1) throw new IllegalArgumentException("Missing expected value in assertion ${IS_EQUAL_TO_METHOD}()")
            def expected = argumentList[0]
            return ObjectAssertions.isEqualTo(this, objectUnderTest, expected)
        } else if (assertionName.startsWith(HAS_ASSERTION_PREFIX) && assertionName.endsWith(THAT_ASSERTION_POSTFIX)) {
            def fieldName = Extractors.extractFieldName(assertionName, HAS_ASSERTION_PREFIX, THAT_ASSERTION_POSTFIX)
            return assertHasFieldThat(this, objectUnderTest, fieldName, assertionName)
        } else if (assertionName.startsWith(HAS_EMPTY_ASSERTION_PREFIX)) {
            def fieldName = Extractors.extractFieldName(assertionName, HAS_EMPTY_ASSERTION_PREFIX)
            return assertEmptyField(this, objectUnderTest, fieldName, assertionName)
        } else if (assertionName.startsWith(HAS_ASSERTION_PREFIX)) {
            if (argumentList == null || argumentList.length < 1) throw new IllegalArgumentException("Missing expected value in assertion ${assertionName}()")
            def fieldName = Extractors.extractFieldName(assertionName, HAS_ASSERTION_PREFIX)
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
        } else if (field !instanceof Number && field !instanceof String && field instanceof Object) {
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
        return new DynamicAssertions(object, assertionObject)
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