package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.enums.CourseType;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.repositories.CourseRepository;
import com.learn.techplatform.services.Course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.COURSE_API)
public class CourseController extends AbstractBaseController {
    @Autowired
    CourseService courseService;

    @PostMapping(ApiPath.ADD)
    @Operation(summary = "Create new course")
    ResponseEntity<RestAPIResponse<Object>> createCourse(@RequestBody CourseDTO courseDTO) {
        courseService.createCourse(courseDTO);
        return responseUtil.successResponse("OK!");
    }

    @DeleteMapping(ApiPath.DELETE + ApiPath.ID)
    @Operation(summary = "Delete course")
    ResponseEntity<RestAPIResponse<Object>> deleteCourse(@PathVariable("id") String id) {
        courseService.deleteCourse(id);
        return responseUtil.successResponse("OK!");
    }
}
