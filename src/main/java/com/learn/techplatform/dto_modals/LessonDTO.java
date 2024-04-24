package com.learn.techplatform.dto_modals;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.controllers.models.request.CreateLessonRequest;
import com.learn.techplatform.controllers.models.request.EditLessonRequest;
import com.learn.techplatform.entities.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class LessonDTO {
    private String id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private long duration;
    private LessonType lessonType;
    private String content;
    private String question;
    private int numericalOrder;
    private String chapterId;
    private String videoId;

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.slug = lesson.getSlug();
        this.thumbnailUrl = lesson.getThumbnailUrl();
        this.duration = lesson.getDuration();
        this.lessonType = lesson.getLessonType();

    }

    public LessonDTO(EditLessonRequest editLessonRequest) {
        this.title = editLessonRequest.getTitle();
        this.thumbnailUrl = editLessonRequest.getThumbnailUrl();
        this.duration = editLessonRequest.getDuration();
        this.content = editLessonRequest.getContent();
        this.question = editLessonRequest.getQuestion();
        this.numericalOrder = editLessonRequest.getNumericalOrder();
        this.lessonType = LessonType.valueOf(editLessonRequest.getLessonType());
        this.chapterId = editLessonRequest.getChapterId();
        this.videoId = editLessonRequest.getVideoId();
    }

    public LessonDTO(CreateLessonRequest createLessonRequest, LessonType lessonType) {
        this.title = createLessonRequest.getTitle();
        this.thumbnailUrl = createLessonRequest.getThumbnailUrl();
        this.duration = createLessonRequest.getDuration();
        this.content = createLessonRequest.getContent();
        this.question = createLessonRequest.getQuestion();
        this.numericalOrder = createLessonRequest.getNumericalOrder();
        this.lessonType = lessonType;
        this.chapterId = createLessonRequest.getChapterId();
        this.videoId = createLessonRequest.getVideoId();
    }
}
