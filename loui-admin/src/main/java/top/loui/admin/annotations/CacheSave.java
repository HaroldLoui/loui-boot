package top.loui.admin.annotations;


import top.loui.admin.enums.CacheType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 新增缓存对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface CacheSave {

    /**
     * 缓存名称
     */
    String name() default "";

    /**
     * 缓存名称（唯一）
     */
    String key();

    /**
     * 缓存类型
     */
    CacheType cacheType() default CacheType.NONE;

    /**
     * 本地缓存过期时间，等于0则以全局的过期时间为准
     */
    long localExpire() default 0L;

    /**
     * 本地缓存过期时间单位
     */
    TimeUnit localExpireTimeUnit() default TimeUnit.SECONDS;

    /**
     * 远程缓存过期时间，等于0则以全局的过期时间为准
     */
    long remoteExpire() default 0L;

    /**
     * 远程缓存过期时间单位
     */
    TimeUnit remoteExpireTimeUnit() default TimeUnit.SECONDS;

    /**
     * 是否允许缓存null值
     */
    boolean allowNullValues() default true;
}
