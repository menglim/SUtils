package com.github.menglim.sutils.annotations.ajaxrequest;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AjaxRequestValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AjaxRequest {
    String locale() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
