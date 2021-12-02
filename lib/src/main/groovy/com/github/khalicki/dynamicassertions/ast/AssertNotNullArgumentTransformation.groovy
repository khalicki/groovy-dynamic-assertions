package com.github.khalicki.dynamicassertions.ast

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.BooleanExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.AssertStatement
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation


@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class AssertNotNullArgumentTransformation implements ASTTransformation  {
    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        MethodNode method = (MethodNode) nodes[1]
        def existingStatements = ((BlockStatement)method.code).statements
        def assertion = createAssertStatement("title", ClassHelper.STRING_TYPE)
        def endMessage = createPrintlnAst("Ending $method.name")
        existingStatements.add(endMessage)
        existingStatements.push(assertion)
    }

    private static Statement createAssertStatement(String variableName, ClassNode type) {
        return new AssertStatement(
            new BooleanExpression(
                new BinaryExpression(
                    new VariableExpression(variableName, type),
                    new Token(Types.COMPARE_EQUAL, "==", 0, 0),
                    new VariableExpression("expected", type)
                )
            )
        )
    }

    private static Statement createPrintlnAst(String message) {
        new ExpressionStatement(
            new MethodCallExpression(
                new VariableExpression("this"),
                new ConstantExpression("println"),
                new ArgumentListExpression(
                    new ConstantExpression(message)
                )
            )
        )
    }
}
