package top.loui.admin.config.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@EnableCaching
@Configuration
public class RedisConfig {

    @Bean
    public JsonRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new JsonRedisTemplate(connectionFactory);
    }
}
