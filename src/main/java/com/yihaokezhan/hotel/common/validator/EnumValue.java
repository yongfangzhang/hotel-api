package com.yihaokezhan.hotel.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author zhangyongfang
 * @since Mon Mar 01 2021
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {
    String message() default "{custom.value.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String getValueMethod() default "getValue";

    boolean canBeZero() default false;

    boolean canBeNull() default false;

    class Validator implements ConstraintValidator<EnumValue, Object> {

        private Class<? extends Enum<?>> enumClass;
        private String getValueMethod;
        private boolean canBeZero;
        private boolean canBeNull;

        @Override
        public void initialize(EnumValue enumValue) {
            getValueMethod = enumValue.getValueMethod();
            enumClass = enumValue.enumClass();
            canBeZero = enumValue.canBeZero();
            canBeNull = enumValue.canBeNull();
        }

        @Override
        public boolean isValid(Object value,
                ConstraintValidatorContext constraintValidatorContext) {
            if (canBeNull && value == null) {
                return Boolean.TRUE;
            }
            if (value == null || enumClass == null || getValueMethod == null) {
                return Boolean.FALSE;
            }
            if (canBeZero && NumberUtils.isCreatable(value.toString())) {
                int intValue = 0;
                try {
                    intValue = Integer.parseInt(value.toString());
                } catch (NumberFormatException ignored) {
                }
                if (intValue == 0) {
                    return true;
                }
            }
            return EnumValueCheck.checkValue(enumClass, getValueMethod, value);
        }

    }
}
