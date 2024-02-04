package top.loui.admin.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import top.loui.admin.annotations.NotEqualZero;

public class NotEqualZeroValidator implements ConstraintValidator<NotEqualZero, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && value != 0L;
    }
}
