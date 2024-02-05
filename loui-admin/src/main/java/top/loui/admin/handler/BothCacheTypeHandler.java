package top.loui.admin.handler;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.loui.admin.annotations.CacheSave;
import top.loui.admin.common.LocalCacheWrapper;
import top.loui.admin.config.properties.RemoteProperties;
import top.loui.admin.utils.RedisUtils;

import java.util.concurrent.TimeUnit;

/**
 * 二级缓存处理
 */
@RequiredArgsConstructor
@Component("BOTH")
public class BothCacheTypeHandler extends AbstractCacheTypeHandler {

    private final Cache<String, LocalCacheWrapper> caffeineCache;
    private final RemoteProperties remoteProperties;

    @Override
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        final String key = getKey(joinPoint);
        // 先从本地级缓存中获取
        LocalCacheWrapper existWrapper = caffeineCache.getIfPresent(key);
        if (existWrapper != null) {
            return existWrapper.object();
        }
        MethodSignature signature  = (MethodSignature) joinPoint.getSignature();
        CacheSave annotation = signature.getMethod().getAnnotation(CacheSave.class);
        // 从远程缓存中获取
        if (RedisUtils.hasKey(key)) {
            Object object = RedisUtils.getCacheObject(key);
            // 放进本地缓存
            long expire = annotation.localExpire();
            TimeUnit timeUnit = annotation.localExpireTimeUnit();
            LocalCacheWrapper wrapper = new LocalCacheWrapper(expire, timeUnit, object);
            caffeineCache.put(key, wrapper);
            return object;
        }
        Object proceed = joinPoint.proceed();
        // 检查是否允许缓存null值
        checkNullValue(annotation.allowNullValues(), proceed);
        // 放入远程缓存中
        long remoteExpire = annotation.remoteExpire();
        TimeUnit remoteTimeUnit = annotation.remoteExpireTimeUnit();
        if (remoteTimeUnit.toNanos(remoteExpire) == 0L) {
            remoteExpire = remoteProperties.getExpire();
            remoteTimeUnit = remoteProperties.getUnit();
        }
        RedisUtils.setCacheObject(key, proceed, remoteExpire, remoteTimeUnit);
        // 放入本地缓存中
        long localExpire = annotation.localExpire();
        TimeUnit localExpireTimeUnit = annotation.localExpireTimeUnit();
        LocalCacheWrapper wrapper = new LocalCacheWrapper(localExpire, localExpireTimeUnit, proceed);
        caffeineCache.put(key, wrapper);
        return proceed;
    }
}
