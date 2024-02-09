package top.loui.admin.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存对象包装器
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalCacheWrapper {

    /**
     * 缓存过期时间
     */
    private long expire;

    /**
     * 缓存过期时间单位
     */
    private TimeUnit unit;

    /**
     * 缓存对象
     */
    private Object object;
}
