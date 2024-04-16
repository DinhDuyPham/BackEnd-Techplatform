package com.learn.techplatform.services.Course;

import com.learn.techplatform.entities.Course;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.data.jpa.repository.JpaRepository;

public class CourseServiceImpl extends AbstractBaseService<Course, String> implements CourseService {
    public CourseServiceImpl(JpaRepository<Course, String> genericRepository) {
        super(genericRepository);
    }
}
