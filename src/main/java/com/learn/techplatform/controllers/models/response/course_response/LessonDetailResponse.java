package com.learn.techplatform.controllers.models.response.course_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.LessonStatus;
import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.entities.Lesson;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class LessonDetailResponse {
    private String title;
    private String thumbnailUrl;
    private int numericalOrder;
    private long duration;
    private LessonType type;
    private LessonStatus status;

    public LessonDetailResponse(Lesson lesson) {
        this.title = lesson.getTitle();
        this.thumbnailUrl = lesson.getThumbnailUrl();
        this.numericalOrder = lesson.getNumericalOrder();
        this.duration = lesson.getDuration();
        this.type = lesson.getLessonType();
        this.status = lesson.getLessonStatus();
    }
}
