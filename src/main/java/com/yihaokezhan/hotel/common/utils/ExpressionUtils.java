package com.yihaokezhan.hotel.common.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionUtils {

    public static Object eval1(StandardEvaluationContext ctx, String expression) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        return ctx != null ? exp.getValue(ctx) : exp.getValue();
    }

    public static Object eval1(String expression) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        return exp.getValue();
    }

    public static Object[] eval(StandardEvaluationContext ctx, String... expressions) {
        ExpressionParser parser = new SpelExpressionParser();
        Object[] result = new Object[expressions.length];
        int index = 0;
        for (String expression : expressions) {
            result[index] = ctx != null ? parser.parseExpression(expression).getValue(ctx)
                    : parser.parseExpression(expression).getValue();
            index++;
        }
        return result;
    }

    public static Object[] eval(String... expressions) {
        ExpressionParser parser = new SpelExpressionParser();
        Object[] result = new String[expressions.length];
        int index = 0;
        for (String expression : expressions) {
            result[index] = parser.parseExpression(expression).getValue();
            index++;
        }
        return result;
    }
}
