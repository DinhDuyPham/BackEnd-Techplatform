package com.learn.techplatform.services.Course;

import com.learn.techplatform.common.enums.CourseType;
import com.learn.techplatform.common.enums.GenderType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.DateUtil;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl extends AbstractBaseService<Course, String> implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    public CourseServiceImpl(JpaRepository<Course, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public PagingResponse getPageCourse(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(sortType, "title"));
        orders.add(new Sort.Order(sortTypeDate, "updatedDate"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        PagingResponse pagingResponse = new PagingResponse(courseRepository.getPageCourse("%" + searchKey + "%", pageable));
        return pagingResponse;
    }

    @Override
    public void createCourse(CourseDTO courseDTO) {
        boolean isCourseExist = courseRepository.existsByTitle(courseDTO.getTitle());
        Validator.mustTrue(!isCourseExist, RestAPIStatus.EXISTED, RestStatusMessage.COURSE_ALREADY_EXISTED);

        Validator.notNullAndNotEmpty(courseDTO.getTitle(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_TITLE_FORMAT);
        Validator.notNullAndNotEmpty(courseDTO.getDescription(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT);
        Validator.notNull(courseDTO.getPrice(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT);
        Validator.notNullAndNotEmpty(courseDTO.getContent(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT);

        Course course = Course.builder()
                .id(UniqueID.getUUID())
                .title(courseDTO.getTitle())
                .description(courseDTO.getDescription())
                .price(courseDTO.getPrice())
                .content(courseDTO.getContent())
                .slug(StringUtils.slugify(courseDTO.getTitle()))
                .courseType(CourseType.NONE)
                .thumbnailUrl(null)
                .discount(0)
                .viewed(0)
                .systemStatus(SystemStatus.ACTIVE)
                .build();

        this.save(course);
    }

    @Override
    public void editCourse(String id, CourseDTO courseDTO) {
        Course course = courseRepository.findCourseByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);

        if (courseDTO.getTitle() != null) {
            boolean isTitleValid = Validator.checkNotNullAndNotEmptyString(courseDTO.getTitle());
            Validator.mustTrue(isTitleValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_TITLE_FORMAT);
            course.setTitle(courseDTO.getTitle());
            course.setSlug(StringUtils.slugify(courseDTO.getTitle()));
        }

        if (courseDTO.getThumbnailUrl() != null) {
            boolean isThumbnailUrlValid = Validator.checkNotNullAndNotEmptyString(courseDTO.getThumbnailUrl());
            Validator.mustTrue(isThumbnailUrlValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_UPLOAD_IMAGE);
            course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        }

        if (courseDTO.getDescription() != null) {
            boolean isDescriptionValid = Validator.checkNotNullAndNotEmptyString(courseDTO.getDescription());
            Validator.mustTrue(isDescriptionValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT);
            course.setDescription(courseDTO.getDescription());
        }

        if (!Validator.checkNull(courseDTO.getPrice())) {
            boolean isPriceValid = Validator.checkNotNull(courseDTO.getPrice());
            Validator.mustTrue(isPriceValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_PRICE_FORMAT);
            course.setPrice(courseDTO.getPrice());
        }

        if (courseDTO.getContent() != null) {
            boolean isContentValid = Validator.checkNotNullAndNotEmptyString(courseDTO.getContent());
            Validator.mustTrue(isContentValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_CONTENT_FORMAT);
            course.setContent(courseDTO.getContent());
        }

        if (!Validator.checkNull(courseDTO.getDiscount())) {
            boolean isDiscountValid = Validator.checkNotNull(courseDTO.getDiscount());
            Validator.mustTrue(isDiscountValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DISCOUNT_FORMAT);
            course.setDiscount(courseDTO.getDiscount());
        }

        if (Validator.checkNull(courseDTO.getCourseType()))
            course.setCourseType(null);
        else {
            boolean isCourseTypeValid = Validator.checkNotNullAndNotEmptyString(courseDTO.getCourseType());
            Validator.mustTrue(isCourseTypeValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_COURSE_TYPE_FORMAT);
            course.setCourseType(courseDTO.getCourseType());
        }

        this.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findCourseByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        course.setSystemStatus(SystemStatus.INACTIVE);
        this.save(course);
    }
}
