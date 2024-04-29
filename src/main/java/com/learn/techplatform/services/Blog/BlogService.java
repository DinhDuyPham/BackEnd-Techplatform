package com.learn.techplatform.services.Blog;

import com.learn.techplatform.controllers.models.request.EditBlogRequest;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.services.InterfaceBaseService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BlogService extends InterfaceBaseService<Blog, String> {
    void createBlog(BlogDTO blogDTO, String userId);
    void editBlog(String id,EditBlogRequest editBlogRequest);
    void deleteBlog(String id);
    List<Blog> getAllBlogs();
    PagingResponse getPageBlog(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey);
}