package com.learn.techplatform.services.LessonQuestion;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.LessonQuestion;
import com.learn.techplatform.services.InterfaceBaseService;

import java.util.List;

public interface LessonQuestionService extends InterfaceBaseService<LessonQuestion, String> {
    List<LessonQuestion> getByLessonId(String lessonId);

}
