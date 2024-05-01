package com.learn.techplatform.dto_modals.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.entities.CourseHistory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CourseDetailInformationDTO {
    private boolean isRegistered;
    private String lastLessonId;
    private CourseDTO course;
    private List<CourseChapterListDTO> chapters;

    public CourseDetailInformationDTO(Course course) {
        this.course = new CourseDTO(course);
        this.chapters = new ArrayList<CourseChapterListDTO>();
        this.isRegistered = false;
    }

    public CourseDetailInformationDTO(Course course, CourseHistory courseHistory) {
        this.course = new CourseDTO(course);
        this.chapters = new ArrayList<CourseChapterListDTO>();
        this.lastLessonId = courseHistory.getCurrentLessonId();
        this.isRegistered = true;
    }

    public CourseDetailInformationDTO(Course course, boolean isRegistered) {
        this.course = new CourseDTO(course);
        this.chapters = new ArrayList<CourseChapterListDTO>();
        this.isRegistered = isRegistered;
    }

}
