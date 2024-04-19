package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.CourseType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findCourseByIdAndCourseType(String id, CourseType type);
    Course findCourseByIdAndSystemStatus(String id, SystemStatus systemStatus);
    boolean existsByTitle(String title);
}
