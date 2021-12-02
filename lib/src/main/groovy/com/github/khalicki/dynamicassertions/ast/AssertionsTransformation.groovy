package com.github.khalicki.dynamicassertions.ast


import groovy.transform.CompileStatic
import groovyjarjarasm.asm.Opcodes
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.BooleanExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.AssertStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.apache.groovy.ast.tools.AnnotatedNodeUtils.markAsGenerated
import static org.codehaus.groovy.ast.tools.GeneralUtils.param
import static org.codehaus.groovy.ast.tools.GeneralUtils.params

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class AssertionsTransformation implements ASTTransformation {

    public static final ClassNode[] NO_EXCEPTIONS = ClassNode.EMPTY_ARRAY;
    public static final Parameter[] NO_PARAMS = Parameter.EMPTY_ARRAY;

    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        ClassNode classNode = (ClassNode) nodes[1]
        def methodNode = createHasFieldMethod("title", ClassHelper.STRING_TYPE)
        addGeneratedMethod(classNode, methodNode)
    }

    private void addGeneratedMethod(ClassNode classNode, MethodNode methodNode) {
        classNode.addMethod(methodNode)
        markAsGenerated(classNode, methodNode)
    }

    static MethodNode createHasFieldMethod(String fieldName, ClassNode fieldType) {
        def methodName = "has" + upperCaseFirstLetter(fieldName)
        Parameter parameter = param(fieldType, "expected");
        def assertStatement = createAssertStatement(fieldName, fieldType)
        return new MethodNode(
            methodName,
            Opcodes.ACC_PUBLIC,
            ClassHelper.VOID_TYPE,
            params(parameter),
            NO_EXCEPTIONS,
            GeneralUtils.block(
                assertStatement
            )
        )
    }

    private static String upperCaseFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
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
}
