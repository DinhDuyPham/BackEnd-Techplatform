package com.learn.techplatform.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.CourseType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditCourseRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String thumbnailUrl;
    private String description;
    @NotBlank
    private float price;
    @NotBlank
    private String content;
    @NotBlank
    private CourseType courseType;
    private float discount;
}
