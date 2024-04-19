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
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends AbstractBaseService<Course, String> implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    public CourseServiceImpl(JpaRepository<Course, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public void createCourse(CourseDTO courseDTO) {
        boolean isCourseExist = courseRepository.existsByTitle(courseDTO.getTitle());
        Validator.mustTrue(!isCourseExist, RestAPIStatus.EXISTED, RestStatusMessage.COURSE_ALREADY_EXISTED);

        Validator.notNullAndNotEmpty(courseDTO.getTitle(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_TITLE_FORMAT);
        Validator.notNullAndNotEmpty(courseDTO.getDescription(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT);
        Validator.mustEqual(0, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT, courseDTO.getPrice());
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
