package com.github.khalicki.dynamicassertions

class DynamicAssertions implements GroovyInterceptable {
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
        if (name == "asBoolean") return true
        if (name.startsWith("has")) {
            if (args == null) throw new IllegalArgumentException("Missing expected value")
            def fieldName = extractFieldName(name)
            def expected = args[0]
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

    static String extractFieldName(String assertionMethodName) {
        def firstLetter = assertionMethodName.charAt(3).toLowerCase().toString()
        def tail = assertionMethodName.substring(4)
        return firstLetter + tail
    }
}
