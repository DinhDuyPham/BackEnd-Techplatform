package com.learn.techplatform.common.validations.xss;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = XSSValidator.class)
@Documented
public @interface NoXSS {

    String message() default "Contain string is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}