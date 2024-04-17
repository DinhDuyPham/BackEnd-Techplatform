package com.learn.techplatform.services.LessonQuestion;

import com.learn.techplatform.entities.LessonQuestion;
import com.learn.techplatform.repositories.LessonQuestionRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonQuestionServiceImpl extends AbstractBaseService<LessonQuestion, String> implements LessonQuestionService {
    @Autowired
    LessonQuestionRepository lessonQuestionRepository;

    public LessonQuestionServiceImpl(JpaRepository<LessonQuestion, String> genericRepository) {
        super(genericRepository);
    }
}
