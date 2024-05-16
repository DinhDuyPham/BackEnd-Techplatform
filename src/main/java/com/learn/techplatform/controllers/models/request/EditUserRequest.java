package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.GenderType;
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
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String firstName;
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String lastName;
    private GenderType gender;
    @Pattern(regexp = Constant.PHONE_NUMBER_REGEX, message = ParamError.PATTERN)
    private String phoneNumber;
    private String dateOfBirth;
    private String bio;
}
