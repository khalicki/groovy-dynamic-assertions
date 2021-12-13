package com.github.khalicki.dynamicassertions.ast

class ASTAssertionDebug {
    String title = "When Harry met Sally"

    @DebuggingMethod
    def hasTitle(String expected) {
        assert this.title == expected
        return this
    }
}
