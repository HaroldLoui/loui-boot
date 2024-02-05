package top.loui.admin.handler;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.loui.admin.annotations.CacheSave;
import top.loui.admin.config.properties.RemoteProperties;
import top.loui.admin.utils.RedisUtils;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component("REMOTE")
public class RemoteCacheTypeHandler extends AbstractCacheTypeHandler {

    private final RemoteProperties remoteProperties;

    @Override
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        final String key = getKey(joinPoint);
        if (RedisUtils.hasKey(key)) {
            return RedisUtils.getCacheObject(key);
        }
        Object proceed = joinPoint.proceed();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CacheSave annotation = signature.getMethod().getAnnotation(CacheSave.class);
        // 检查是否允许缓存null值
        checkNullValue(annotation.allowNullValues(), proceed);
        // 获取过期时间
        long expire = annotation.remoteExpire();
        TimeUnit timeUnit = annotation.remoteExpireTimeUnit();
        if (timeUnit.toNanos(expire) == 0L) {
            expire = remoteProperties.getExpire();
            timeUnit = remoteProperties.getUnit();
        }
        RedisUtils.setCacheObject(key, proceed, expire, timeUnit);
        return proceed;
    }
}