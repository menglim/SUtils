package com.github.menglim.sutils.annotations.notempty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyXSSValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyXSS {

    String locale() default "";

    String message() default "Field is required";

    boolean stripHtml() default true;

    boolean removeSpace() default false;

    boolean allowedNull() default false;

    int length() default -1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
