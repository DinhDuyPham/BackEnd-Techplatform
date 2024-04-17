package com.learn.techplatform.services.Course;

import com.learn.techplatform.entities.Course;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends AbstractBaseService<Course, String> implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    public CourseServiceImpl(JpaRepository<Course, String> genericRepository) {
        super(genericRepository);
    }
}
