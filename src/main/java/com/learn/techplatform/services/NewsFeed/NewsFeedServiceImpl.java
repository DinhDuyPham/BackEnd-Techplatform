package com.learn.techplatform.services.NewsFeed;

import com.learn.techplatform.entities.NewsFeed;
import com.learn.techplatform.repositories.NewsFeedRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsFeedServiceImpl extends AbstractBaseService<NewsFeed, String> implements NewsFeedService {
    @Autowired
    NewsFeedRepository newsFeedRepository;

    public NewsFeedServiceImpl(JpaRepository<NewsFeed, String> genericRepository) {
        super(genericRepository);
    }
}
