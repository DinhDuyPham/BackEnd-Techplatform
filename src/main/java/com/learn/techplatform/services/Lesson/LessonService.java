package com.learn.techplatform.services.Lesson;

import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.services.InterfaceBaseService;
import org.springframework.data.domain.Sort;

public interface LessonService extends InterfaceBaseService<Lesson, String> {
    PagingResponse getPageLesson(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey);
    LessonDTO getLessonById(String id);
    void createVideoLesson(LessonDTO lessonDTO);
    void editLesson(String id, LessonDTO lessonDTO);
    void deleteLesson(String id);
}
