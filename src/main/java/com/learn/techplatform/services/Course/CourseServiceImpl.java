package com.learn.techplatform.services.Course;

import com.google.gson.JsonObject;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.*;
import com.learn.techplatform.common.exceptions.ApplicationException;
import com.learn.techplatform.common.restfullApi.HttpRequestUtil;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.AppValueConfigure;
import com.learn.techplatform.common.utils.DateUtil;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.controllers.models.response.PaymentCourseResponse;
import com.learn.techplatform.controllers.models.response.TokenResponse;
import com.learn.techplatform.controllers.models.response.UserCourseRegisterResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.dto_modals.course.CourseChapterListDTO;
import com.learn.techplatform.dto_modals.course.CourseDetailInformationDTO;
import com.learn.techplatform.entities.*;
import com.learn.techplatform.helper.ChapterHelper;
import com.learn.techplatform.helper.SessionHelper;
import com.learn.techplatform.repositories.ChapterRepository;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.repositories.LessonRepository;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.AbstractBaseService;
import com.learn.techplatform.services.CourseHistory.CourseHistoryService;
import com.learn.techplatform.services.OrderHistory.OrderHistoryService;
import com.learn.techplatform.services.Session.SessionService;
import com.learn.techplatform.services.User.UserService;
import com.learn.techplatform.vietqr_bank.modals.GenerateVietQrRequest;
import com.learn.techplatform.vietqr_bank.modals.VietQrResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    OrderHistoryService orderHistoryService;
    @Autowired
    SessionHelper sessionHelper;
    @Autowired
    SessionService sessionService;

    @Autowired
    ChapterHelper chapterHelper;

    private static final HttpRequestUtil httpRequestUtil = new HttpRequestUtil();


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

    @Override
    public TokenResponse paymentCourse(String courseId, String userId) {
        Course course = this.courseRepository.findByIdAndSystemStatus(courseId, SystemStatus.ACTIVE);
        Validator.notNull(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.COURSE_NOT_FOUND);
        CourseHistory courseHistory = courseHistoryService.getByCourseIdAndUserID(courseId, userId);
        Validator.mustTrue(course.getPrice() > 0 && courseHistory == null, RestAPIStatus.FAIL, RestStatusMessage.FAIL);
        OrderHistory orderHistory = OrderHistory.builder()
                .id(UniqueID.getUUID())
                .systemStatus(SystemStatus.ACTIVE)
                .userId(userId)
                .courseId(courseId)
                .status(OrderHistoryStatus.PENDING)
                .build();
        this.orderHistoryService.save(orderHistory);
        Session session = sessionHelper.createSession(userId, course.getId(), DateUtil.getUTCNow().getTime() + DateUtil.FIVE_MINUTE, SessionType.PAYMENT_COURSE);
        sessionService.save(session);
        return TokenResponse.builder()
                .token(session.getId())
                .expireTime(session.getExpireTime())
                .build();
    }

    @Override
    public PaymentCourseResponse getPaymentCourseInfo(String token, AppValueConfigure appValueConfigure) {
        Session session = sessionService.getByIdAndSystemStatus(token, SystemStatus.ACTIVE);
        Validator.notNull(session, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOT_FOUND);
        UserDTO user = userService.getAuthInfo(session.getUserId());
        Validator.notNull(user, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOT_FOUND);
        Course course = this.courseRepository.findByIdAndSystemStatus(session.getData(), SystemStatus.ACTIVE);
        Validator.notNull(course, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOT_FOUND);

        try {
            GenerateVietQrRequest generateVietQrRequest = GenerateVietQrRequest.builder()
                    .accountName(appValueConfigure.bankAccountName)
                    .accountNo(appValueConfigure.bankAccountNo)
                    .acqId(appValueConfigure.bankAcqId)
                    .amount(course.getPrice())
                    .template(VietQrTemplate.TP_QR)
                    .addInfo(course.getCode()+" "+user.getUsername())
                    .build();
            //        Header

            Map<String, String> headers = new HashMap<>();
            headers.put("x-client-id", appValueConfigure.vietQrClientId);
            headers.put("x-api-key", appValueConfigure.getVietQrApikey);
            headers.put("Content-Type", "application/json; charset=utf-8");

            HttpResponse response = httpRequestUtil.postRequest(
                    new StringEntity(generateVietQrRequest.toJSONObject().toString(), StandardCharsets.UTF_8),
                    appValueConfigure.vietQrApiUrl,
                    ContentType.APPLICATION_JSON,
                    headers
            );
            String bodyRes = getResponseBody(response);
            JSONObject dataObj = new JSONObject(bodyRes);

            VietQrResponse vietQrResponse = new VietQrResponse(dataObj);
            return PaymentCourseResponse.builder()
                    .qrCode(vietQrResponse.getData().getQrCode())
                    .qrDataURL(vietQrResponse.getData().getQrDataURL())
                    .accountName(generateVietQrRequest.getAccountName())
                    .accountNo(generateVietQrRequest.getAccountNo())
                    .addInfo(generateVietQrRequest.getAddInfo())
                    .amount(generateVietQrRequest.getAmount())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException(RestAPIStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        return this.courseRepository.getByCodeAndSystemStatus(courseCode, SystemStatus.ACTIVE);
    }


    private UserDTO getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader(Constant.HEADER_TOKEN);
        if(token == null) {
            return null;
        }
        return this.userService.getAuthInfoFromToken(token);
    }

    private static String getResponseBody(HttpResponse response) {
        if (response == null) {
            log.error("response is null");
            throw new ApplicationException(RestAPIStatus.INTERNAL_SERVER_ERROR);
        }
        String responseBody;
        try {
            responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException(RestAPIStatus.INTERNAL_SERVER_ERROR);
        }

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new ApplicationException(RestAPIStatus.VIETQR_API_ERROR, responseBody);
        }

        return responseBody;
    }
}
