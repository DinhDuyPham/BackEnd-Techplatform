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
import com.learn.techplatform.controllers.models.response.course_response.ChapterDetailResponse;
import com.learn.techplatform.controllers.models.response.course_response.CourseDetailResponse;
import com.learn.techplatform.controllers.models.response.course_response.LessonDetailResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.repositories.ChapterRepository;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.repositories.LessonRepository;
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
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    LessonRepository lessonRepository;

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

        boolean isTitleExist = courseRepository.existsByTitle(courseDTO.getTitle());
        Validator.mustTrue(!isTitleExist, RestAPIStatus.EXISTED, RestStatusMessage.COURSE_ALREADY_EXISTED);
        course.setTitle(courseDTO.getTitle());
        course.setSlug(StringUtils.slugify(courseDTO.getTitle()));

        course.setThumbnailUrl(courseDTO.getThumbnailUrl());

        course.setDescription(courseDTO.getDescription());
        if (Validator.checkNull(courseDTO.getDescription()))
            course.setDescription(course.getDescription());

        course.setPrice(courseDTO.getPrice());

        course.setContent(courseDTO.getContent());
        if (Validator.checkNull(courseDTO.getContent()))
            course.setContent(course.getContent());

        course.setCourseType(courseDTO.getCourseType());
        if (Validator.checkNull(courseDTO.getCourseType()))
            course.setCourseType(course.getCourseType());

        course.setDiscount(courseDTO.getDiscount());
        if (Validator.checkNull(courseDTO.getDiscount()))
            course.setDiscount(course.getDiscount());

        this.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findCourseByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        course.setSystemStatus(SystemStatus.INACTIVE);
        this.save(course);
    }

    @Override
    public CourseDetailResponse getCourseDetailById(String id) {
        Course course = courseRepository.findCourseByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        List<ChapterDetailResponse> chapters = chapterRepository.getChaptersByCourseIdAndSystemStatus(id);
        List<LessonDetailResponse> lessons = lessonRepository.getLessonsByCourseIdAndSystemStatus(id);

        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getCourseId() == id) {}
                chapters.get(i).setLessons(lessons);
        }

        CourseDetailResponse courseDetailResponse = CourseDetailResponse.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .content(course.getContent())
                .chapters(chapters)
                .build();

        return courseDetailResponse;
    }
}
