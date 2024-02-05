package top.loui.admin.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import top.loui.admin.annotations.CacheSave;
import top.loui.admin.exceptions.BusinessException;

import java.lang.reflect.Method;

/**
 * 处理缓存类型抽象类
 */
public abstract class AbstractCacheTypeHandler {

    /**
     * 处理方法
     */
    public abstract Object handler(ProceedingJoinPoint joinPoint) throws Throwable;

    /**
     * 获取缓存的键名称
     */
    protected String getKey(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CacheSave annotation = method.getAnnotation(CacheSave.class);
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        // 表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 解析出一个表达式
        Expression expression = parser.parseExpression(annotation.key());
        // 开始准备表达式运行环境
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            ctx.setVariable(parameterNames[i], args[i]);
        }
        return annotation.name() + expression.getValue(ctx);
    }

    /**
     * 是否允许缓存null值
     */
    protected void checkNullValue(boolean allowNullValue, Object object) {
        if (!allowNullValue && object == null) {
            throw new BusinessException("The cache value is null");
        }
    }
}
