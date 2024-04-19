package com.learn.techplatform.repositories;

import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {
    Course findBlogById(String id);
    boolean existsByTitle(String title);
}
