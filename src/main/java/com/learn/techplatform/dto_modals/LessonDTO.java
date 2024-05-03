package com.learn.techplatform.dto_modals;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.LessonStatus;
import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.controllers.models.request.CreateLessonRequest;
import com.learn.techplatform.controllers.models.request.EditLessonRequest;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.entities.LessonQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class LessonDTO {
    private String id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private long duration;
    private LessonType lessonType;
    private LessonStatus lessonStatus;
    private String content;
    private String question;
    private int numericalOrder;
    private String chapterId;
    private String videoId;
    private String injectHtml;
    private List<LessonQuestion> answers;

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.slug = lesson.getSlug();
        this.duration = lesson.getDuration();
        this.lessonStatus = lesson.getLessonStatus();
        this.chapterId = lesson.getChapterId();
        this.numericalOrder = lesson.getNumericalOrder();
        this.lessonType = lesson.getLessonType();
    }

    public LessonDTO(Lesson lesson, boolean isExpland) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.slug = lesson.getSlug();
        this.duration = lesson.getDuration();
        this.lessonStatus = lesson.getLessonStatus();
        this.chapterId = lesson.getChapterId();
        this.numericalOrder = lesson.getNumericalOrder();
        this.lessonType = lesson.getLessonType();
        if(isExpland) {
            this.question = lesson.getQuestion();
            this.videoId = lesson.getVideoId();
            this.lessonType = lesson.getLessonType();
            this.thumbnailUrl = lesson.getThumbnailUrl();
            this.content = lesson.getContent();
            this.injectHtml = lesson.getInjectHtml();

        }
    }

    public LessonDTO(Lesson lesson, List<LessonQuestion> lessonQuestion) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.slug = lesson.getSlug();
        this.duration = lesson.getDuration();
        this.lessonStatus = lesson.getLessonStatus();
        this.chapterId = lesson.getChapterId();
        this.numericalOrder = lesson.getNumericalOrder();
        this.question = lesson.getQuestion();
        this.videoId = lesson.getVideoId();
        this.lessonType = lesson.getLessonType();
        this.thumbnailUrl = lesson.getThumbnailUrl();
        this.content = lesson.getContent();
        this.injectHtml = lesson.getInjectHtml();
        if(this.lessonType == LessonType.QUESTION) {
            this.answers = new ArrayList<>();
            this.answers.addAll(lessonQuestion);
        }
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
        this.lessonStatus = LessonStatus.LOCKED;
    }
}
