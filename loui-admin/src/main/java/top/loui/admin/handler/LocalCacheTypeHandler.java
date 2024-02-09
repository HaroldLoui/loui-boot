package top.loui.admin.handler;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.loui.admin.annotations.CacheSave;
import top.loui.admin.common.LocalCacheWrapper;
import top.loui.admin.utils.AspectUtils;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存处理
 */
@RequiredArgsConstructor
@Component("LOCAL")
public class LocalCacheTypeHandler extends AbstractCacheTypeHandler {

    private final Cache<String, LocalCacheWrapper> caffeineCache;

    @Override
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        final String key = AspectUtils.parseCacheKey(joinPoint, CacheSave.class, CacheSave::key, CacheSave::name);
        LocalCacheWrapper existWrapper = caffeineCache.getIfPresent(key);
        if (existWrapper != null) {
            return existWrapper.getObject();
        }
        MethodSignature signature  = (MethodSignature) joinPoint.getSignature();
        CacheSave annotation = signature.getMethod().getAnnotation(CacheSave.class);
        Object proceed = joinPoint.proceed();
        // 检查是否允许缓存null值
        checkNullValue(annotation.allowNullValues(), proceed);
        long expire = annotation.localExpire();
        TimeUnit timeUnit = annotation.localExpireTimeUnit();
        LocalCacheWrapper wrapper = new LocalCacheWrapper(expire, timeUnit, proceed);
        caffeineCache.put(key, wrapper);
        return proceed;
    }
}
