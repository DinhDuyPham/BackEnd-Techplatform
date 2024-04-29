package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {
    Blog findBlogByIdAndSystemStatus(String id, SystemStatus systemStatus);
    boolean existsByTitle(String title);

    @Query("""
        select b
        from Blog b
        where b.title like :search_key
    """)
    Page<Blog> getPageBlog(@Param("search_key") String searchKey, Pageable pageable);
}
