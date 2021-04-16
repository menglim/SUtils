package com.github.menglim.sutils.annotations.navigation;


import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Navigation {
    Class clazz() default Void.class;

    String name() default "";

    boolean menu() default true;

    String icon() default "";

    boolean anonymous() default false;

    boolean slideMenu() default false;

    boolean dashboardMenu() default false;

}
