package top.loui.admin.annotations;

import top.loui.admin.enums.CacheType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 修改缓存对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface CacheUpdate {

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
}
