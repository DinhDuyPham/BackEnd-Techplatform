package com.learn.techplatform.services.Lesson;

import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.services.InterfaceBaseService;

public interface LessonService extends InterfaceBaseService<Lesson, String> {
    LessonDTO getVideoLessonById(String id);
    void createVideoLesson(LessonDTO lessonDTO);
}
