package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditUserRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String firstName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String lastName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 5, message = ParamError.MAX_LENGTH)
    private String gender;

    @Pattern(regexp = Constant.PHONE_NUMBER_REGEX, message = ParamError.PATTERN)
    @Size(max = 15, message = ParamError.MAX_LENGTH)
    private String phoneNumber;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 10, message = ParamError.MAX_LENGTH)
    private String dateOfBirth;

    private String bio;

    private String profileImage;

    private String coverImage;
}
