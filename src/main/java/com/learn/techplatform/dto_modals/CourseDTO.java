package com.learn.techplatform.dto_modals;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.CourseType;
import com.learn.techplatform.controllers.models.request.EditCourseRequest;
import com.learn.techplatform.entities.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CourseDTO {
    private String id;
    private String title;
    private String thumbnailUrl;
    private String slug;
    private int viewed;
    private String description;
    private float price;
    private String content;
    private CourseType courseType;
    private float discount;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.thumbnailUrl = course.getThumbnailUrl();
        this.slug = course.getSlug();
        this.viewed = course.getViewed();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.content = course.getContent();
        this.courseType = course.getCourseType();
        this.discount = course.getDiscount();
    }

    public CourseDTO(EditCourseRequest editCourseRequest) {
        this.title = editCourseRequest.getTitle();
        this.thumbnailUrl = editCourseRequest.getThumbnailUrl();
        this.description = editCourseRequest.getDescription();
        this.price = editCourseRequest.getPrice();
        this.content = editCourseRequest.getContent();
        this.courseType = editCourseRequest.getCourseType();
        this.discount = editCourseRequest.getDiscount();
    }
}
