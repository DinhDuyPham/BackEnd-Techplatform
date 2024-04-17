package com.learn.techplatform.repositories;

import com.learn.techplatform.entities.CourseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseHistoryRepository extends JpaRepository<CourseHistory, String> {
}
