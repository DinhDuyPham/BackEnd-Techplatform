package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.EditLessonRequest;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.services.Lesson.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.LESSON_API)
public class LessonController extends AbstractBaseController{
    @Autowired
    LessonService lessonService;

    @GetMapping()
    @Operation(summary = "Get All Lesson")
    ResponseEntity<RestAPIResponse<Object>> getAllLesson() {
        return responseUtil.successResponse(lessonService.getAll());
    }

    @GetMapping(ApiPath.VIDEO + ApiPath.ID)
    @Operation(summary = "Get Video Lesson By ID")
    ResponseEntity<RestAPIResponse<Object>> getVideoLessonById(@PathVariable("id") String id) {
        return responseUtil.successResponse(lessonService.getVideoLessonById(id));
    }

    @PostMapping(ApiPath.VIDEO + ApiPath.ADD)
    @Operation(summary = "Create new video lesson")
    ResponseEntity<RestAPIResponse<Object>> createLesson(@RequestBody @Valid EditLessonRequest editLessonRequest) {
        LessonDTO lessonDTO = new LessonDTO(editLessonRequest, LessonType.VIDEO);
        lessonService.createVideoLesson(lessonDTO);
        return responseUtil.successResponse("OK!");
    }
}
