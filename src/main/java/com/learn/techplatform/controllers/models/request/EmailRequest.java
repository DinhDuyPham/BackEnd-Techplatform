package com.learn.techplatform.controllers.models.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailRequest {

    @NotBlank(message = ParamError.FIELD_NAME)
    @Pattern(regexp = Constant.EMAIL_REGEX, message = ParamError.PATTERN)
    @Size(max = 120, message = ParamError.MAX_LENGTH)
    private String email;
}
