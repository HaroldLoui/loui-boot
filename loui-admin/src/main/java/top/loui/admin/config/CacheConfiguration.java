package top.loui.admin.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.loui.admin.common.LocalCacheWrapper;
import top.loui.admin.config.properties.LocalProperties;
import top.loui.admin.utils.JsonUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties({LocalProperties.class})
@Configuration
@Slf4j
public class CacheConfiguration {

    @Resource
    private LocalProperties localProperties;

    @Bean
    public Cache<String, LocalCacheWrapper> caffeineCache() {
        return Caffeine.newBuilder()
            // 自定义过期策略。
            .expireAfter(new Expiry<String, LocalCacheWrapper>() {
                /**
                 *  指定一旦条目创建后的持续时间过了，就应该自动从缓存中删除该条目。
                 */
                @Override
                public long expireAfterCreate(String s, LocalCacheWrapper wrapper, long l) {
                    // 全局配置的过期时间
                    long globalExpire = localProperties.getExpire();
                    TimeUnit globalExpireUnit = localProperties.getUnit();
                    long globalNanos = globalExpireUnit.toNanos(globalExpire);
                    // 单个key配置的过期时间
                    long nanos = wrapper.unit().toNanos(wrapper.expire());
                    // 相等则已全局配置为准
                    long times = nanos == 0L ? globalNanos : Math.max(nanos, -1L);
                    // 记录日志
                    if (localProperties.isPrintLog()) {
                        String object = JsonUtils.toJsonString(wrapper.object());
                        long expireTime = Duration.ofNanos(times).toSeconds();
                        log.info(StrUtil.format("key: {}, value: {}, expireTime: {}", s, object, expireTime));
                    }
                    return times;
                }

                /**
                 *  指定在更新其值后的持续时间一过，就应自动从缓存中删除该条目。可以返回currentDuration(l1)来不修改过期时间。
                 */
                @Override
                public long expireAfterUpdate(String s, LocalCacheWrapper wrapper, long l, @NonNegative long l1) {
                    return l1;
                }

                /**
                 *   指定超过最后一次读取后的持续时间，就应自动从缓存中删除该条目。可以返回currentDuration(l1)来不修改过期时间。
                 */
                @Override
                public long expireAfterRead(String s, LocalCacheWrapper wrapper, long l, @NonNegative long l1) {
                    if (localProperties.isPrintLog()) {
                        String object = JsonUtils.toJsonString(wrapper.object());
                        long expireTime = Duration.ofNanos(l1).toSeconds();
                        log.info(StrUtil.format("key: {}, value: {}, expireTime: {}", s, object, expireTime));
                    }
                    return l1;
                }
            })
            .build();
    }
}
