package top.loui.admin.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * 不启用缓存
 */
@Component("NONE")
public class NoneCacheTypeHandler extends AbstractCacheTypeHandler {

    @Override
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
