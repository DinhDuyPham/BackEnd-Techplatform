package com.learn.techplatform.services.CourseHistory;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.request.UpdateLastLessonRequest;
import com.learn.techplatform.entities.CourseHistory;
import com.learn.techplatform.repositories.CourseHistoryRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseHistoryImpl extends AbstractBaseService<CourseHistory, String> implements CourseHistoryService {
    @Autowired
    CourseHistoryRepository courseHistoryRepository;

    public CourseHistoryImpl(JpaRepository<CourseHistory, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public CourseHistory getByCourseIdAndUserID(String courseId, String userID) {
        return this.courseHistoryRepository.findByCourseIdAndUserIdAndSystemStatus(courseId, userID, SystemStatus.ACTIVE);
    }

    @Override
    public CourseHistory getByUserID(String userID) {
        return this.courseHistoryRepository.findByUserIdAndSystemStatus(userID, SystemStatus.ACTIVE);
    }

    @Override
    public CourseHistory updateTheLastLesson(UpdateLastLessonRequest updateLastLessonRequest,String courseId, String userID) {
        CourseHistory courseHistory = this.courseHistoryRepository.findByCourseIdAndUserIdAndSystemStatus(courseId, userID, SystemStatus.ACTIVE);
        Validator.notNull(courseHistory, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOT_FOUND);
        courseHistory.setCurrentLessonId(updateLastLessonRequest.getCurrentLessonId());
        this.save(courseHistory);
        return courseHistory;
    }
}
