package com.learn.techplatform.controllers.models.response.course_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.entities.Chapter;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ChapterDetailResponse {
    private String title;
    private int numericalOrder;
    private int totalLesson;
    private List<LessonDetailResponse> lessons;
    private String courseId;

    public ChapterDetailResponse(Chapter chapter) {
        this.title = chapter.getTitle();
        this.numericalOrder = chapter.getNumericalOrder();
        this.lessons = new ArrayList<>();
        this.totalLesson = lessons.size();
        this.courseId = chapter.getCourseId();
    }

    public ChapterDetailResponse(Chapter chapter, List<LessonDetailResponse> lessons) {
        this.title = chapter.getTitle();
        this.numericalOrder = chapter.getNumericalOrder();
        this.totalLesson = lessons.size();
        this.lessons = lessons;
        this.courseId = chapter.getCourseId();
    }
}
