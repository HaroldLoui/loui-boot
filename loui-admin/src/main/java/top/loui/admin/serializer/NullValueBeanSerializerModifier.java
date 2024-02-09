package top.loui.admin.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.Collection;
import java.util.List;

/**
 * 空值序列化修改器
 *
 * @author hanjinfeng
 */
public class NullValueBeanSerializerModifier extends BeanSerializerModifier {

    public static final NullValueBeanSerializerModifier INSTANCE = new NullValueBeanSerializerModifier();

    private NullValueBeanSerializerModifier() {
    }

    /**
     * 注册null值序列化器
     */
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            if (isArrayType(writer)) {
                // 给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(NullArraySerializer.INSTANCE);
            }
        }
        return beanProperties;

    }

    /**
     * 是否是数组
     */
    private boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }
}
