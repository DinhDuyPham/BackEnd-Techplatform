package com.learn.techplatform.services.Blog;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.controllers.models.request.EditBlogRequest;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.repositories.BlogRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.repositories.BlogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BlogServiceImpl extends AbstractBaseService<Blog, String> implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    public BlogServiceImpl(JpaRepository<Blog, String> genericRepository) {super(genericRepository);}


    @Override
    public void createBlog(BlogDTO blogDTO, String userId) {
        boolean existsByTitle = blogRepository.existsByTitle(blogDTO.getTitle());
        Validator.mustTrue(!existsByTitle,RestAPIStatus.EXISTED, RestStatusMessage.BLOG_ALREADY_EXISTED);
        Validator.notNullAndNotEmpty(blogDTO.getTitle(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_TITLE_FORMAT);
        Validator.notNullAndNotEmpty(blogDTO.getContent(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_CONTENT_FORMAT);

        Blog blog = Blog.builder()
                .id(UniqueID.getUUID())
                .title(blogDTO.getTitle())
                .content(blogDTO.getContent())
                .liked(0)
                .viewed(0)
                .userId(userId)
                .systemStatus(SystemStatus.ACTIVE)
                .build();
        this.save(blog);
    }
    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public PagingResponse getPageBlog(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(sortType, "title"));
        orders.add(new Sort.Order(sortTypeDate, "createdDate"));
        orders.add(new Sort.Order(sortTypeDate, "updatedDate"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        PagingResponse pagingResponse = new PagingResponse(blogRepository.getPageBlog("%" + searchKey + "%", pageable));
        return pagingResponse;
    }

    @Override
    public void editBlog(String id, EditBlogRequest editBlogRequest) {
        Blog existingBlog = blogRepository.findBlogByIdAndSystemStatus(id, SystemStatus.ACTIVE);

        if (existingBlog == null) {
            Validator.notNullAndNotEmpty(existingBlog, RestAPIStatus.NOT_FOUND, RestStatusMessage.BLOG_NOT_FOUND);
        }

        existingBlog.setTitle(editBlogRequest.getTitle());
        existingBlog.setContent(editBlogRequest.getContent());

        blogRepository.save(existingBlog);
    }
    @Override
    public void deleteBlog(String id) {
        Blog blog = blogRepository.findBlogByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(blog, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        blog.setSystemStatus(SystemStatus.INACTIVE);
        this.save(blog);
    }
}
