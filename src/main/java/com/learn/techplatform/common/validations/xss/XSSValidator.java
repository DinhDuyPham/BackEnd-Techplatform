package com.learn.techplatform.common.validations.xss;

import com.learn.techplatform.common.constants.Constant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XSSValidator implements ConstraintValidator<NoXSS, String> {
    public static final Pattern VALID_XSS = Pattern.compile(Constant.XSS_PATTERN, Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(NoXSS noXSS) {
        //TODO nothing to do in here
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean valid;
        Matcher matcher = VALID_XSS.matcher(value);
        valid = matcher.matches();
        return valid;
    }
}