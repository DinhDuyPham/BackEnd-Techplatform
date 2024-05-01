package com.learn.techplatform.security;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.*;

@Parameter(hidden = true)
@Retention( RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface AuthSession {
    /**
     * If true then throw ApplicationException when access token header not found
     *
     * @return
     */
    boolean required() default true;
}
