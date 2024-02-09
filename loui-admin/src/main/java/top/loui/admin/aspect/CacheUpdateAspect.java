package top.loui.admin.aspect;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.loui.admin.annotations.CacheUpdate;
import top.loui.admin.common.LocalCacheWrapper;
import top.loui.admin.config.properties.LocalProperties;
import top.loui.admin.config.properties.RemoteProperties;
import top.loui.admin.enums.CacheType;
import top.loui.admin.utils.AspectUtils;
import top.loui.admin.utils.RedisUtils;

@RequiredArgsConstructor
@Aspect
@Component
public class CacheUpdateAspect {

    private final Cache<String, LocalCacheWrapper> caffeineCache;
    private final LocalProperties localProperties;
    private final RemoteProperties remoteProperties;

    @Pointcut("@annotation(top.loui.admin.annotations.CacheUpdate)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CacheUpdate annotation = signature.getMethod().getAnnotation(CacheUpdate.class);
        CacheType cacheType = annotation.cacheType();
        final String key = AspectUtils.parseCacheKey(joinPoint, CacheUpdate.class, CacheUpdate::key, CacheUpdate::name);
        switch (cacheType) {
            case LOCAL:
                handleLocal(key, proceed);
                break;
            case REMOTE:
                handleRemote(key, proceed);
                break;
            case BOTH:
                handleBoth(key, proceed);
                break;
            default:
                break;
        }
        return proceed;
    }

    private void handleLocal(String key, Object value) {
        // 更新一级缓存
        LocalCacheWrapper newWrapper = new LocalCacheWrapper();
        newWrapper.setExpire(localProperties.getExpire());
        newWrapper.setUnit(localProperties.getUnit());
        newWrapper.setObject(value);
        caffeineCache.put(key, newWrapper);
    }

    private void handleRemote(String key, Object value) {
        // 更新二级缓存
        RedisUtils.setCacheObject(key, value, remoteProperties.getExpire(), remoteProperties.getUnit());
    }

    private void handleBoth(String key, Object value) {
        // 更新一级缓存
        LocalCacheWrapper newWrapper = new LocalCacheWrapper();
        newWrapper.setExpire(localProperties.getExpire());
        newWrapper.setUnit(localProperties.getUnit());
        newWrapper.setObject(value);
        caffeineCache.put(key, newWrapper);
        // 更新二级缓存
        RedisUtils.setCacheObject(key, value, remoteProperties.getExpire(), remoteProperties.getUnit());
    }
}
