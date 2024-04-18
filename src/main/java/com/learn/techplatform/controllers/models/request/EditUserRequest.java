package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditUserRequest {
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String firstName;

    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String lastName;

    @Pattern(regexp = Constant.EMAIL_REGEX, message = ParamError.PATTERN)
    @Size(max = 120, message = ParamError.MAX_LENGTH)
    private String email;

    private String gender;

    @Pattern(regexp = Constant.PHONE_NUMBER_REGEX, message = ParamError.PATTERN)
    @Size(max = 11, message = ParamError.MAX_LENGTH)
    private String phoneNumber;

    @Size(max = 10, message = ParamError.MAX_LENGTH)
    private String dateOfBirth;

    @Size(max = 300, message = ParamError.MAX_LENGTH)
    private String bio;

    private String profileImage;

    private String coverImage;
}
