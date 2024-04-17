package com.learn.techplatform.repositories;

import com.learn.techplatform.entities.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedRepository extends JpaRepository<NewsFeed, String> {
}
