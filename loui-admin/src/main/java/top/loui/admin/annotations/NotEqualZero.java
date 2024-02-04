package top.loui.admin.annotations;

import jakarta.validation.Constraint;
import top.loui.admin.common.NotEqualZeroValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { NotEqualZeroValidator.class })
public @interface NotEqualZero {

    String message() default "过期时间不能设置为0";
}
