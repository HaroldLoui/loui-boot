package top.loui.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.date.DatePattern;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.boot.json.JacksonJsonParser;
import top.loui.admin.exceptions.BusinessException;
import top.loui.admin.serializer.BigNumberSerializer;
import top.loui.admin.serializer.NullValueBeanSerializerModifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * json工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class JsonUtils {

    private static final ObjectMapper MAPPER = initMapper();

    /**
     * 初始化ObjectMapper
     */
    private static ObjectMapper initMapper() {
        JsonMapper mapper = JsonMapper.builder()
            // 处理数字转换、处理时间
            .addModules(numberModule(), timeModule())
            // 下划线转驼峰
            // .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            // 将enum通过其toString方法转换为字符串
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            // 大小写脱敏
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            // 允许以0开头的数字
            .enable(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS)
            // 允许以+开头的数字
            .enable(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS)
            // 允许以小数点开头的小数
            .enable(JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS)
            // 关闭空对象不让序列化功能
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            // 忽略未知字段
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
        // 处理Null值数组为空数组
        handleNullArrayValue(mapper);
        return mapper;
    }

    /**
     * 处理大数的序列化
     */
    private static Module numberModule() {
        SimpleModule numberModule = new SimpleModule();
        numberModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
        numberModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
        numberModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
        numberModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        return numberModule;
    }

    /**
     * 处理时间的序列化
     */
    private static Module timeModule() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(YearMonth.class, new YearMonthSerializer(DatePattern.NORM_MONTH_FORMATTER));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        return timeModule;
    }

    /**
     * 处理null值数组为空数组
     */
    private static void handleNullArrayValue(final ObjectMapper mapper) {
        SerializerFactory factory = mapper.getSerializerFactory();
        mapper.setSerializerFactory(factory.withSerializerModifier(NullValueBeanSerializerModifier.INSTANCE));
    }

    /**
     * 将对象转为json字符串
     *
     * @param object 对象
     * @return json字符串
     */
    public static String toJsonString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 将json字符串解析成指定类型的对象
     *
     * @param text  json字符串
     * @param clazz 类对象
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return MAPPER.readValue(text, clazz);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 将json字符串解析成指定类型的Optional对象
     *
     * @param text  json字符串
     * @param clazz 类对象
     * @param <T>   泛型
     * @return Optional对象
     */
    public static <T> Optional<T> parseObjectOpt(String text, Class<T> clazz) {
        try {
            return Optional.of(MAPPER.readValue(text, clazz));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 将json字符串解析成指定类型的对象列表
     *
     * @param text  json字符串
     * @param clazz 类对象
     * @param <T>   泛型
     * @return 对象列表
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StrUtil.isEmpty(text)) {
            return new ArrayList<>();
        }
        try {
            return MAPPER.readValue(text, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 将对象object转为Map对象
     *
     * @param object object对象
     * @return map
     */
    public static Map<String, Object> parseMap(Object object) {
        try {
            JacksonJsonParser parser = new JacksonJsonParser(MAPPER);
            String json = MAPPER.writeValueAsString(object);
            return parser.parseMap(json);
        } catch (JsonProcessingException e) {
            return Collections.emptyMap();
        }
    }
}
