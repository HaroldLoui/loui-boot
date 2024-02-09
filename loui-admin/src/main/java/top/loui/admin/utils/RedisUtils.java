package top.loui.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import top.loui.admin.config.redis.JsonRedisTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具类
 *
 * @author hanjinfeng
 */
@SuppressWarnings({"unchecked"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisUtils {

    private static final JsonRedisTemplate REDIS_TEMPLATE = SpringUtil.getBean(JsonRedisTemplate.class);

    /**
     * 获取所有满足匹配字符串的Key
     *
     * @param pattern 匹配字符串
     * @return keys集合
     */
    public static Set<String> keys(String pattern) {
        if (StrUtil.isEmpty(pattern)) {
            return Collections.emptySet();
        }
        return REDIS_TEMPLATE.keys(pattern);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                REDIS_TEMPLATE.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true存在，false不存在
     */
    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(REDIS_TEMPLATE.hasKey(key));
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return Optional.ofNullable(REDIS_TEMPLATE.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
    }

    /**
     * 放入缓存，永久有效
     *
     * @param key    键
     * @param object 对象数据
     */
    public static void setCacheObject(String key, Object object) {
        REDIS_TEMPLATE.opsForValue().set(key, object);
    }

    /**
     * 放入缓存
     *
     * @param key      键
     * @param object   对象数据
     * @param duration 持续时间
     */
    public static void setCacheObject(String key, Object object, Duration duration) {
        REDIS_TEMPLATE.opsForValue().set(key, object, duration);
    }

    /**
     * 放入缓存
     *
     * @param key      键
     * @param object   对象数据
     * @param time     持续时间，若小于设置无期限
     * @param timeUnit 持续时间单位
     */
    public static void setCacheObject(String key, Object object, long time, TimeUnit timeUnit) {
        if (time > 0) {
            REDIS_TEMPLATE.opsForValue().set(key, object, Duration.of(time, timeUnit.toChronoUnit()));
        } else {
            REDIS_TEMPLATE.opsForValue().set(key, object);
        }
    }

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @param <T> 泛型
     * @return 对象数据
     */
    public static <T> T getCacheObject(String key) {
        return (T) REDIS_TEMPLATE.opsForValue().get(key);
    }

    /**
     * 删除单个缓存
     *
     * @param key 键
     * @return true-删除成功，false-删除失败
     */
    public static boolean delete(String key) {
        return Boolean.TRUE.equals(REDIS_TEMPLATE.delete(key));
    }

    /**
     * 批量删除缓存
     *
     * @param keys 键集合
     * @return true-删除成功，false-删除失败
     */
    public static boolean delete(String... keys) {
        return delete(Arrays.asList(keys));
    }

    /**
     * 批量删除缓存
     *
     * @param keys 键集合
     * @return true-删除成功，false-删除失败
     */
    public static boolean delete(Collection<String> keys) {
        Long delete = REDIS_TEMPLATE.delete(keys);
        return delete != null && delete > 0L;
    }
}
