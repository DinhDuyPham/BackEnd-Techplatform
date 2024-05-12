package com.learn.techplatform.services.Course;

import com.learn.techplatform.common.utils.AppValueConfigure;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.controllers.models.response.PaymentCourseResponse;
import com.learn.techplatform.controllers.models.response.TokenResponse;
import com.learn.techplatform.controllers.models.response.UserCourseRegisterResponse;
import com.learn.techplatform.controllers.models.response.course_response.CourseDetailResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.dto_modals.course.CourseDetailInformationDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.InterfaceBaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CourseService  extends InterfaceBaseService<Course, String> {
    PagingResponse getPageCourse(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey);
    void createCourse(CourseDTO courseDTO);
    void editCourse(String id, CourseDTO courseDTO);
    void deleteCourse(String id);
    CourseDetailInformationDTO getCourseDetailById(String id, AuthUser authUser);
    List<CourseDTO> getCourseDTO();

    CourseDetailInformationDTO getCourseDetailInformationBySlug(String slug, HttpServletRequest request);
    UserCourseRegisterResponse registerCourse(String courseId, String userId);
    TokenResponse paymentCourse(String courseId, String userId);
    PaymentCourseResponse getPaymentCourseInfo(String token, AppValueConfigure appValueConfigure);

    Course getCourseByCode(String courseCode);
}
