package com.github.khalicki.dynamicassertions

class Extractors {

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
