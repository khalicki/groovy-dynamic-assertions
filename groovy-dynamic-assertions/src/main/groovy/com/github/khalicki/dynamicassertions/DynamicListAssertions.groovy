package com.github.khalicki.dynamicassertions

/**
 * Class implements dynamic assertions on list of object
 *
 * Use static method assertThat() to start building assertions in tests
 * @author kamil.halicki
 */
class DynamicListAssertions implements GroovyInterceptable, AssertionNode {
    public static final String AS_BOOLEAN_METHOD = "asBoolean"
    public static final String IS_EMPTY_METHOD = "isEmpty"
    public static final String IS_NOT_EMPTY_METHOD = "isNotEmpty"
    public static final String HAS_SIZE_METHOD = "hasSize"
    public static final String CONTAINS_METHOD = "contains"
    public static final String CONTAINS_ITEM_WITH_PREFIX = "containsItemWith"

    private List listUnderTest
    private AssertionNode parentAssertion

    DynamicListAssertions(List listUnderTest, AssertionNode parent) {
        this.listUnderTest = listUnderTest
        this.parentAssertion = parent
    }

    static DynamicListAssertions assertThat(List listUnderTest) {
        return new DynamicListAssertions(listUnderTest, null)
    }

    @Override
    Object invokeMethod(String assertionName, Object args) {
        if (assertionName == AS_BOOLEAN_METHOD) return true
        Object[] argumentList = (Object[]) args
        if (assertionName == AssertionNodeAssertions.AND_METHOD) {
            return AssertionNodeAssertions.and(this, parentAssertion)
        } else if (assertionName == ObjectAssertions.IS_NULL_METHOD) {
            return ObjectAssertions.isNull(this, listUnderTest)
        } else if (assertionName == ObjectAssertions.IS_NOT_NULL_METHOD) {
            return ObjectAssertions.isNotNull(this, listUnderTest)
        } else if (assertionName == IS_EMPTY_METHOD) {
            return isEmpty(this)
        } else if (assertionName == IS_NOT_EMPTY_METHOD) {
            return isNotEmpty(this)
        } else if (assertionName == HAS_SIZE_METHOD) {
            return hasSize(this, argumentList)
        } else if (assertionName == CONTAINS_METHOD) {
            return contains(this, argumentList)
        } else if (assertionName.startsWith(CONTAINS_ITEM_WITH_PREFIX)) {
            return containsItemWith(this, argumentList, assertionName)
        } else {
            throw new MissingMethodException(assertionName, DynamicListAssertions.class, args)
        }
    }

    static void requireExpectedValue(Object[] argumentList, String assertionMethod) {
        if (argumentList == null || argumentList.length < 1) throw new IllegalArgumentException("Missing expected value in assertion ${assertionMethod}()")
    }

    static DynamicListAssertions contains(DynamicListAssertions listAssertions, Object[] argumentList) {
        requireExpectedValue(argumentList, CONTAINS_METHOD)
        def listUnderTest = listAssertions.listUnderTest
        def expected = argumentList[0]
        assert listUnderTest.contains(expected)
        return listAssertions
    }

    static DynamicListAssertions isEmpty(DynamicListAssertions listAssertions) {
        def listUnderTest = listAssertions.listUnderTest
        assert listUnderTest != null
        assert listUnderTest.isEmpty()
        return listAssertions
    }

    static DynamicListAssertions isNotEmpty(DynamicListAssertions listAssertions) {
        def listUnderTest = listAssertions.listUnderTest
        assert listUnderTest != null
        assert !listUnderTest.isEmpty()
        return listAssertions
    }

    static DynamicListAssertions hasSize(DynamicListAssertions listAssertions, Object[] argumentList) {
        requireExpectedValue(argumentList, HAS_SIZE_METHOD)
        def listUnderTest = listAssertions.listUnderTest
        def expectedSize = argumentList[0]
        assert listUnderTest.size() == expectedSize
        return listAssertions
    }

    static DynamicListAssertions containsItemWith(DynamicListAssertions listAssertions, Object[] argumentList, String methodName) {
        requireExpectedValue(argumentList, methodName)
        def propertyName = Extractors.extractFieldName(methodName, CONTAINS_ITEM_WITH_PREFIX)
        def listUnderTest = listAssertions.listUnderTest
        def expected = argumentList[0]
        assert listUnderTest.any { it[propertyName] == expected }
        return listAssertions
    }
}
