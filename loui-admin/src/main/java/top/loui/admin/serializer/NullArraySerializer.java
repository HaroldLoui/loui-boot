package top.loui.admin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * null值数组序列化方式
 *
 * @author hanjinfeng
 */
public class NullArraySerializer extends JsonSerializer<Object> {

    /**
     * 当前实例
     */
    public static final NullArraySerializer INSTANCE = new NullArraySerializer();

    /**
     * 序列化方式
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeEndArray();
    }
}
