package com.github.khalicki.dynamicassertions

interface AssertionNode {
}

class NoParentAssertion extends RuntimeException {
    NoParentAssertion(AssertionNode assertionNode) {
        super("Cannot get parent assertion from assertion '$assertionNode'")
    }
}
