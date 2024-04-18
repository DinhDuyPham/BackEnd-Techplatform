package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(min = 6, message = ParamError.MIN_LENGTH)
    private String currentPassword;
    
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(min = 6, message = ParamError.MIN_LENGTH)
    private String newPassword;
    
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(min = 6, message = ParamError.MIN_LENGTH)
    private String confirmPassword;
}
