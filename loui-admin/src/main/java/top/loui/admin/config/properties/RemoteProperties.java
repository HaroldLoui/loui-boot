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
@ConfigurationProperties(prefix = "cache.remote")
public class RemoteProperties {

    /**
     * 远程缓存全局过期时间
     */
    @NotEqualZero
    private long expire = -1L;

    /**
     * 远程缓存全局过期时间单位
     */
    private TimeUnit unit = TimeUnit.SECONDS;
}
