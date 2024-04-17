package com.learn.techplatform.services.Lesson;

import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.repositories.LessonRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl extends AbstractBaseService<Lesson, String> implements LessonService{
    @Autowired
    LessonRepository lessonRepository;

    public LessonServiceImpl(JpaRepository<Lesson, String> genericRepository) {
        super(genericRepository);
    }


}
