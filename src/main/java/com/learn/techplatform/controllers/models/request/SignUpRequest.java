package com.learn.techplatform.controllers.models.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequest {

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String firstName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 50, message = ParamError.MAX_LENGTH)
    private String lastName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Pattern(regexp = Constant.EMAIL_REGEX, message = ParamError.PATTERN)
    @Size(max = 120, message = ParamError.MAX_LENGTH)
    private String email;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 36, message = ParamError.MAX_LENGTH)
    @Size(min = 32, message = ParamError.MIN_LENGTH)
    private String passwordHash;

}
