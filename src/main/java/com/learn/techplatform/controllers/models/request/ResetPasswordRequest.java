package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 36, message = ParamError.MAX_LENGTH)
    @Size(min = 32, message = ParamError.MIN_LENGTH)
    private String password;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 36, message = ParamError.MAX_LENGTH)
    @Size(min = 32, message = ParamError.MIN_LENGTH)
    private String confirmPassword;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String token;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String otp;
}
