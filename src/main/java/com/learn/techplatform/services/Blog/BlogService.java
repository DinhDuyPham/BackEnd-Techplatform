package com.learn.techplatform.services.Blog;

import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.services.InterfaceBaseService;

public interface BlogService extends InterfaceBaseService<Blog, String> {
    void createBlog(BlogDTO blog, String userId);
}
