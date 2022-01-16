package com.github.khalicki.dynamicassertions

class AssertionNodeAssertions {

    public static final String AND_METHOD = "and"

    static AssertionNode and(AssertionNode currentNode, AssertionNode parentNode) {
        if (parentNode != null)
            return parentNode
        else
            throw new NoParentAssertion(currentNode)
    }
}
