package top.loui.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.ObjUtil;
import top.loui.admin.exceptions.BusinessException;

import java.util.function.Supplier;

/**
 * 自定义断言工具类
 *
 * @author hanjinfeng
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class AssertUtils {

    /**
     * 条件为真时，抛出业务异常
     *
     * @param b      条件
     * @param errMsg 异常信息
     */
    public static void isTrue(boolean b, String errMsg) {
        if (b) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 条件为真时，抛出指定异常
     *
     * @param b        条件
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isTrue(boolean b, Supplier<X> supplier) {
        if (b) {
            throw supplier.get();
        }
    }

    /**
     * 条件为假时，抛出业务异常
     *
     * @param b      条件
     * @param errMsg 异常信息
     */
    public static void isFalse(boolean b, String errMsg) {
        if (!b) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 条件为假时，抛出指定异常
     *
     * @param b        条件
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isFalse(boolean b, Supplier<X> supplier) {
        if (!b) {
            throw supplier.get();
        }
    }

    /**
     * 对象为null时，抛出业务异常
     *
     * @param obj    对象
     * @param errMsg 异常信息
     */
    public static void isNull(Object obj, String errMsg) {
        if (ObjUtil.isNull(obj)) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 对象为null时，抛出指定异常
     *
     * @param obj      对象
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isNull(Object obj, Supplier<X> supplier) {
        if (ObjUtil.isNull(obj)) {
            throw supplier.get();
        }
    }

    /**
     * 对象不为null时，抛出业务异常
     *
     * @param obj    对象
     * @param errMsg 异常信息
     */
    public static void isNotNull(Object obj, String errMsg) {
        if (ObjUtil.isNotNull(obj)) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 对象不为null时，抛出指定异常
     *
     * @param obj      对象
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isNotNull(Object obj, Supplier<X> supplier) {
        if (ObjUtil.isNotNull(obj)) {
            throw supplier.get();
        }
    }

    /**
     * 字符串为空白时，抛出业务异常
     *
     * @param cs     字符串
     * @param errMsg 异常信息
     */
    public static void isBlank(CharSequence cs, String errMsg) {
        if (StrUtil.isBlank(cs)) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 字符串为空白时，抛出指定异常
     *
     * @param cs       字符串
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isBlank(CharSequence cs, Supplier<X> supplier) {
        if (StrUtil.isBlank(cs)) {
            throw supplier.get();
        }
    }

    /**
     * 字符串不为空白时，抛出业务异常
     *
     * @param cs     字符串
     * @param errMsg 异常信息
     */
    public static void isNotBlank(CharSequence cs, String errMsg) {
        if (StrUtil.isNotBlank(cs)) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 字符串不为空白时，抛出指定异常
     *
     * @param cs       字符串
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isNotBlank(CharSequence cs, Supplier<X> supplier) {
        if (StrUtil.isNotBlank(cs)) {
            throw supplier.get();
        }
    }

    /**
     * 集合为空时，抛出业务异常
     *
     * @param array  集合
     * @param errMsg 异常信息
     */
    public static void isEmpty(Iterable<?> array, String errMsg) {
        if (CollUtil.isEmpty(array)) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 集合为空时，抛出指定异常
     *
     * @param array  集合
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isEmpty(Iterable<?> array, Supplier<X> supplier) {
        if (CollUtil.isEmpty(array)) {
            throw supplier.get();
        }
    }

    /**
     * 集合不为空时，抛出业务异常
     *
     * @param array  集合
     * @param errMsg 异常信息
     */
    public static void isNotEmpty(Iterable<?> array, String errMsg) {
        if (CollUtil.isNotEmpty(array)) {
            throw new BusinessException(errMsg);
        }
    }

    /**
     * 集合不为空时，抛出指定异常
     *
     * @param array  集合
     * @param supplier 异常类
     */
    public static <X extends RuntimeException> void isNotEmpty(Iterable<?> array, Supplier<X> supplier) {
        if (CollUtil.isNotEmpty(array)) {
            throw supplier.get();
        }
    }
}
