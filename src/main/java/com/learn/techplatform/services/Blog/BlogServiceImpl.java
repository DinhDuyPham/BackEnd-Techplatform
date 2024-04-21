package com.learn.techplatform.services.Blog;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.repositories.BlogRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.repositories.BlogRepository;
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
        return blogRepository.findAll(); // Lấy tất cả các blog từ cơ sở dữ liệu
    }
    @Override
    public void editBlog(String id, BlogDTO blogDTO) {

        if (!id.equals(blogDTO.getId())) {
            Validator.notNullAndNotEmpty(blogDTO.getContent(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_CONTENT_FORMAT);
        }
        Blog blog = blogRepository.findBlogByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(blog, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        this.save(blog);
    }
    @Override
    public void deleteBlog(String id) {
        Blog blog = blogRepository.findBlogByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(blog, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        blog.setSystemStatus(SystemStatus.INACTIVE);
        this.save(blog);
    }
}
