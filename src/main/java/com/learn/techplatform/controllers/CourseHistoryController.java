package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.UpdateLastLessonRequest;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.CourseHistory.CourseHistoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.COURSE_HISTORY_API)
public class CourseHistoryController extends AbstractBaseController {
    @Autowired
    CourseHistoryService courseHistoryService;


    @PostMapping(path = ApiPath.ID + ApiPath.CURRENT_LESSON)
    public ResponseEntity<RestAPIResponse<Object>> saveCourseHistoryLastLesson(@PathVariable("id") String courseId, @Valid @RequestBody UpdateLastLessonRequest updateLastLessonRequest, @AuthSession AuthUser authUser) {
        return this.responseUtil.successResponse(this.courseHistoryService.updateTheLastLesson(updateLastLessonRequest,courseId, authUser.getId()));
    }
}
