package top.loui.admin.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.annotation.Annotation;
import java.util.function.Function;

/**
 * 切面工具类
 */
public class AspectUtils {

    /**
     * 解析自定义缓存注解的缓存key值
     */
    public static <A extends Annotation> String parseCacheKey(JoinPoint joinPoint, Class<A> clazz, Function<A, String> keyFunc, Function<A, String> nameFunc) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        A annotation = signature.getMethod().getAnnotation(clazz);
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        // 表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 解析出一个表达式
        Expression expression = parser.parseExpression(keyFunc.apply(annotation));
        // 开始准备表达式运行环境
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            ctx.setVariable(parameterNames[i], args[i]);
        }
        return nameFunc.apply(annotation) + expression.getValue(ctx);
    }
}
