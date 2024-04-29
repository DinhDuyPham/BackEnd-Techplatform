package com.learn.techplatform.controllers.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ValidateResponse {
    private boolean success;
    private String message;

    public ValidateResponse(boolean success, RestStatusMessage restStatusMessage) {
        this.success = success;
        this.message = restStatusMessage.name();
    }
}
