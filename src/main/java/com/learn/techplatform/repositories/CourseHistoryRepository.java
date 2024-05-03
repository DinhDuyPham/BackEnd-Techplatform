package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.CourseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseHistoryRepository extends JpaRepository<CourseHistory, String> {
    CourseHistory findByCourseIdAndUserIdAndSystemStatus(String courseId, String userId, SystemStatus systemStatus);


    CourseHistory findByUserIdAndSystemStatus(String userId, SystemStatus systemStatus);
}
