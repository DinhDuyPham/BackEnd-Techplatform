package com.learn.techplatform.common.utils;

public interface ParamError {
    String FIELD_NAME = "{fieldName} is ${validatedValue == null ? 'null' : 'empty'}";
    String MAX_LENGTH = "Maximum length {fieldName} is {max} characters";
    String MIN_LENGTH = "Minimum length {fieldName} is {min} characters";
    String MIN_VALUE = "Minimum {fieldName} is {value}";
    String MAX_VALUE = "Maximum {fieldName} is {value}";
    String MIN_NUMBER = "{fieldName} should not be less than {value}";
    String DEFAULT = "{fieldName} default is {value}";
    String MAX_SELECTION = "Maximum Created selection option is {max}";
    String PATTERN = "{fieldName} is incorrect format";
    //user

    String EMAIL_EXISTED = "Email already existed";
    String PHONE_EXISTED = "Phone number phone existed";
    String USER_NOT_FOUND = "User not found";
    String VERIFY_FAIL = "Verify otp failed";
    String EMAIL_OR_PHONE_EMPTY = "Please input your email or phone number!";
    String EMAIL_PASS_INVALID = "Email or Password invalid";
    String PASSWORD_NOT_MATCH = "New Password and Confirm Password not match";
    String CHANGE_PASSWORD_EXPIRED = "Time change password is expired";
}
