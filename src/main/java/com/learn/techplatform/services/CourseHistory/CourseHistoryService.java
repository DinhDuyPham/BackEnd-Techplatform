package com.learn.techplatform.services.CourseHistory;

import com.learn.techplatform.controllers.models.request.UpdateLastLessonRequest;
import com.learn.techplatform.entities.CourseHistory;
import com.learn.techplatform.repositories.CourseHistoryRepository;
import com.learn.techplatform.services.InterfaceBaseService;
import org.springframework.beans.factory.annotation.Autowired;

public interface CourseHistoryService extends InterfaceBaseService<CourseHistory, String>  {
    CourseHistory getByCourseIdAndUserID(String courseId, String userID);

    CourseHistory getByUserID(String userID);

    CourseHistory updateTheLastLesson(UpdateLastLessonRequest updateLastLessonRequest ,String courseId,  String userID);
}
