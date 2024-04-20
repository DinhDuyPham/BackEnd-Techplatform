package com.learn.techplatform.services.Course;

import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.services.InterfaceBaseService;
import org.springframework.data.domain.Sort;

public interface CourseService  extends InterfaceBaseService<Course, String> {
    PagingResponse getPageCourse(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey);
    void createCourse(CourseDTO courseDTO);
    void editCourse(String id, CourseDTO courseDTO);
    void deleteCourse(String id);
}
