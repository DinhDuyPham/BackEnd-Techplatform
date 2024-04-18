package com.learn.techplatform.services.Course;

import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.services.InterfaceBaseService;

public interface CourseService  extends InterfaceBaseService<Course, String> {
    void createCourse(CourseDTO courseDTO);
    void editCourse(String id, CourseDTO courseDTO);
    void deleteCourse(String id);
}
