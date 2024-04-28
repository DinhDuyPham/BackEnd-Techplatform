package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.AuthValidateStatus;
import com.learn.techplatform.common.enums.AuthValidateType;
import com.learn.techplatform.common.enums.FieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateRequest {
    private AuthValidateType type;
    private AuthValidateStatus status;
    private String value;
    private FieldType field;
}
