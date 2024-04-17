package com.learn.techplatform.services.Blog;

import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.repositories.BlogRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends AbstractBaseService<Blog, String> implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    public BlogServiceImpl(JpaRepository<Blog, String> genericRepository) {
        super(genericRepository);
    }
}
