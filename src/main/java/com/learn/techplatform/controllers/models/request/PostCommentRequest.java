package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.CommentType;
import com.learn.techplatform.common.utils.ParamError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCommentRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    private String content;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String rootId;
    private String parentId;
    private CommentType type;
}
