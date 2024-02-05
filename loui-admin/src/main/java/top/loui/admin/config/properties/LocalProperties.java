package top.loui.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import top.loui.admin.annotations.NotEqualZero;

import java.util.concurrent.TimeUnit;

@Validated
@Data
@Component
@ConfigurationProperties(prefix = "cache.local")
public class LocalProperties {

    /**
     * 本地缓存全局过期时间
     */
    @NotEqualZero
    private long expire = -1L;

    /**
     * 本地缓存全局过期时间单位
     */
    private TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 是否打印日志
     */
    private boolean printLog = false;
}