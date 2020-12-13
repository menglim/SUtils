/*
  User: rsopheak
  Date: 1/17/2019
  Time: 3:20 PM
*/

package com.github.menglim.sutils.annotations.doubleformat;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DoubleFormatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleFormat {

    String locale() default "";

    String message() default "Invalid Number";

    double min() default 0;

    String format() default "#";

    boolean round() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
