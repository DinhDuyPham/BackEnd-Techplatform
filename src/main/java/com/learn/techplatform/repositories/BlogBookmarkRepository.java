package com.learn.techplatform.repositories;

import com.learn.techplatform.entities.BlogBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogBookmarkRepository extends JpaRepository<BlogBookmark, String> {
}
