package top.loui.admin.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component("NONE")
public class NoneCacheTypeHandler extends AbstractCacheTypeHandler {

    @Override
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
