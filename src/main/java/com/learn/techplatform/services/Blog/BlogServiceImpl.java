package com.learn.techplatform.services.Blog;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.entities.Blog;
import com.learn.techplatform.repositories.BlogRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.repositories.BlogRepository;


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

        // Tạo đối tượng khóa học từ dữ liệu DTO
        Blog blog = Blog.builder()
                .id(UniqueID.generateKey(32)) // Tạo id mới cho khóa học
                .title(blogDTO.getTitle())
                .content(blogDTO.getContent())
                .liked(0) // Không có like ban đầu
                .viewed(0) // Không có lượt xem ban đầu
                .userId(userId) // tạo userID
                .systemStatus(SystemStatus.ACTIVE)
                .build();

        // Lưu Blog mới vào cơ sở dữ liệu
        this.save(blog);
    }
}
