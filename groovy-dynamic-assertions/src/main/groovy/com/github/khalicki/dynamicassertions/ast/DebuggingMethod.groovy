package com.github.khalicki.dynamicassertions.ast

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["com.github.khalicki.dynamicassertions.ast.DebuggingMethodTransformation"])
public @interface DebuggingMethod {
}
