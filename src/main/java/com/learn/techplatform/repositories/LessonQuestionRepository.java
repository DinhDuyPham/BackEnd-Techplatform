package com.learn.techplatform.repositories;

import com.learn.techplatform.entities.LessonQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonQuestionRepository extends JpaRepository<LessonQuestion, String> {

}
