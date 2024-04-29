package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateNotificationRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    private String title;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String content;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String linked;

    private boolean seen;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String userId;
}
