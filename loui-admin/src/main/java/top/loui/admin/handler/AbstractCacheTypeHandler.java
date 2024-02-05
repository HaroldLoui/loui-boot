package top.loui.admin.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import top.loui.admin.exceptions.BusinessException;

/**
 * 处理缓存类型抽象类
 */
public abstract class AbstractCacheTypeHandler {

    /**
     * 处理方法
     */
    public abstract Object handler(ProceedingJoinPoint joinPoint) throws Throwable;

    /**
     * 是否允许缓存null值
     */
    protected void checkNullValue(boolean allowNullValue, Object object) {
        if (!allowNullValue && object == null) {
            throw new BusinessException("The cache value is null");
        }
    }
}
