package top.loui.admin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Component;
import top.loui.admin.handler.AbstractCacheTypeHandler;
import top.loui.admin.annotations.CacheSave;

@Aspect
@Component
public class CacheSaveAspect {

    @Pointcut("@annotation(top.loui.admin.annotations.CacheSave)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CacheSave annotation = signature.getMethod().getAnnotation(CacheSave.class);
        AbstractCacheTypeHandler handler = SpringUtil.getBean(annotation.cacheType().name());
        return handler.handler(joinPoint);
    }
}
