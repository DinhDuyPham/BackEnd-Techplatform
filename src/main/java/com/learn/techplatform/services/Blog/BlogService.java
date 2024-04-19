package com.learn.techplatform.services.Blog;

import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.services.InterfaceBaseService;

public interface BlogService extends InterfaceBaseService<Blog, String> {
    void deleteBlog(String id);
}
