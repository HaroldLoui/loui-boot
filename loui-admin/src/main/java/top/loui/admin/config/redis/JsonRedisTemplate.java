package top.loui.admin.config.redis;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <a href="https://springdoc.cn/spring-boot-data-redis/">Link...</a>
 */
public class JsonRedisTemplate extends RedisTemplate<String, Object> {

    public JsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 构造函数注入 RedisConnectionFactory，设置到父类
        super.setConnectionFactory(redisConnectionFactory);
        
        // 使用 Jackson 提供的通用 Serializer
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        serializer.configure(mapper -> {
            // 如果涉及到对 java.time 类型的序列化，反序列化那么需要注册 JavaTimeModule
            mapper.registerModule(new JavaTimeModule());
        });
        
        // String 类型的 key/value 序列化
        super.setKeySerializer(StringRedisSerializer.UTF_8);
        super.setValueSerializer(serializer);
        
        // Hash 类型的 key/value 序列化
        super.setHashKeySerializer(StringRedisSerializer.UTF_8);
        super.setHashValueSerializer(serializer);
    }
}