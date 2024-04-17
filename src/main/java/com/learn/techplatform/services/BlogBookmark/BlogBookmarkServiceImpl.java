package com.learn.techplatform.services.BlogBookmark;

import com.learn.techplatform.entities.BlogBookmark;
import com.learn.techplatform.repositories.BlogBookmarkRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BlogBookmarkServiceImpl extends AbstractBaseService<BlogBookmark, String> implements BlogBookmarkService{
    @Autowired
    BlogBookmarkRepository blogBookmarkRepository;

    public BlogBookmarkServiceImpl(JpaRepository<BlogBookmark, String> genericRepository) {
        super(genericRepository);
    }
}
