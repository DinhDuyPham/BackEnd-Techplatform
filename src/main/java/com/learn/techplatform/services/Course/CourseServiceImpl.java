package com.learn.techplatform.services.Course;

import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.CourseHistoryStatus;
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
import com.learn.techplatform.controllers.models.response.UserCourseRegisterResponse;
import com.learn.techplatform.controllers.models.response.course_response.ChapterDetailResponse;
import com.learn.techplatform.controllers.models.response.course_response.CourseDetailResponse;
import com.learn.techplatform.controllers.models.response.course_response.LessonDetailResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.dto_modals.course.CourseChapterListDTO;
import com.learn.techplatform.dto_modals.course.CourseDetailInformationDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.entities.CourseHistory;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.helper.ChapterHelper;
import com.learn.techplatform.repositories.ChapterRepository;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.repositories.LessonRepository;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.AbstractBaseService;
import com.learn.techplatform.services.CourseHistory.CourseHistoryService;
import com.learn.techplatform.services.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl extends AbstractBaseService<Course, String> implements CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    CourseHistoryService courseHistoryService;
    @Autowired
    UserService userService;

    @Autowired
    ChapterHelper chapterHelper;

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
        Validator.notNull(courseDTO.getPrice(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_PRICE_FORMAT);
        Validator.notNullAndNotEmpty(courseDTO.getContent(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_CONTENT_FORMAT);

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
        Course course = courseRepository.findByIdAndSystemStatus(id, SystemStatus.ACTIVE);
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
        Course course = courseRepository.findByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        course.setSystemStatus(SystemStatus.INACTIVE);
        this.save(course);
    }

    @Override
    public CourseDetailInformationDTO getCourseDetailById(String id, AuthUser authUser) {
        CourseDetailInformationDTO course = courseRepository.getCourseDetailByIdAndUserId(id, authUser.getId());
        Validator.notNull(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        List<CourseChapterListDTO> courseChapterListDTOS = this.chapterRepository.getCourseChapterListByCourseId(course.getCourse().getId());
        course.setChapters(courseChapterListDTOS);
        List<LessonDTO> lessonDTOS = this.lessonRepository.getByCourseId(course.getCourse().getId(), true);
        course.setChapters(chapterHelper.mappingChapterLessonDTO(course.getCourse().getId(), courseChapterListDTOS, lessonDTOS));
        return course;
    }

    @Override
    public List<CourseDTO> getCourseDTO() {
        return this.courseRepository.getCourseDTOList();
    }

    @Override
    public CourseDetailInformationDTO getCourseDetailInformationBySlug(String slug, HttpServletRequest request) {
        Course courseDetail = this.courseRepository.getBySlugAndSystemStatus(slug, SystemStatus.ACTIVE);
        Validator.notNull(courseDetail, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        courseDetail.setViewed(courseDetail.getViewed() + 1);
        this.save(courseDetail);
        UserDTO user = getUserFromRequest(request);
        boolean isUserRegistered = false;
        CourseHistory courseHistory = null;
        if(user != null) {
            courseHistory = this.courseHistoryService.getByCourseIdAndUserID(courseDetail.getId(), user.getId());
            isUserRegistered = courseHistory != null;
        }
        CourseDetailInformationDTO response = new CourseDetailInformationDTO(courseDetail, isUserRegistered);
        response.setLastLessonId(courseHistory != null ? courseHistory.getCurrentLessonId() : null);
        List<CourseChapterListDTO> courseChapterListDTOS = this.chapterRepository.getCourseChapterListByCourseId(courseDetail.getId());
        response.setChapters(courseChapterListDTOS);
        List<LessonDTO> lessonDTOS = this.lessonRepository.getByCourseId(courseDetail.getId(), user != null);
        response.setChapters(chapterHelper.mappingChapterLessonDTO(courseDetail.getId(), courseChapterListDTOS, lessonDTOS));
        return response;
    }

    @Override
    public UserCourseRegisterResponse registerCourse(String courseId, String userId) {
        Course course = this.courseRepository.findByIdAndSystemStatus(courseId, SystemStatus.ACTIVE);
        Validator.notNull(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        CourseHistory courseHistory = courseHistoryService.getByCourseIdAndUserID(courseId, userId);
        Lesson lesson = this.lessonRepository.getByNumericalOrderAndSystemStatus(1, SystemStatus.ACTIVE);
        Validator.notNull(lesson, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOT_FOUND);
        if(course.getPrice() == 0 && courseHistory == null) {
             courseHistory = CourseHistory.builder()
                    .courseId(courseId)
                    .userId(userId)
                    .id(UniqueID.getUUID())
                    .systemStatus(SystemStatus.ACTIVE)
                     .currentLessonId(lesson.getId())
                    .build();
            this.courseHistoryService.save(courseHistory);
        }

        return UserCourseRegisterResponse.builder().status(CourseHistoryStatus.REGISTER_SUCCESS).build();
    }


    private UserDTO getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader(Constant.HEADER_TOKEN);
        if(token == null) {
            return null;
        }
        return this.userService.getAuthInfoFromToken(token);
    }
}
