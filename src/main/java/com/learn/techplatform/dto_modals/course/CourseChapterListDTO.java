package com.learn.techplatform.dto_modals.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.Chapter;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CourseChapterListDTO {
    private String id;
    private String title;
    private int numericalOrder;
    private String courseId;
    private List<LessonDTO> lessons;

    public CourseChapterListDTO(Chapter chapter) {
        this.id = chapter.getId();
        this.title = chapter.getTitle();
        this.numericalOrder = chapter.getNumericalOrder();
        this.courseId = chapter.getCourseId();
        this.lessons = new ArrayList<LessonDTO>();
    }
}
