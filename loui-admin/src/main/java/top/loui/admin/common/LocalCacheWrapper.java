package top.loui.admin.common;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存对象包装器
 *
 * @param expire 缓存过期时间
 * @param unit   缓存过期时间单位
 * @param object 缓存对象
 */
public record LocalCacheWrapper(long expire, TimeUnit unit, Object object) {

}
