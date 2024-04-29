package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.EditCourseRequest;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.services.Course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.COURSE_API)
public class CourseController extends AbstractBaseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    @Operation(summary = "Get All Course")
    ResponseEntity<RestAPIResponse<Object>> getAllCourse()
    {
        return responseUtil.successResponse(courseService.getAll());
    }

    @GetMapping(ApiPath.ID)
    @Operation(summary = "Get Details of Course And Lessons With Chapter By Course ID")
    ResponseEntity<RestAPIResponse<Object>> getCourseDetailById(@PathVariable("id") String id)
    {
        return responseUtil.successResponse(courseService.getCourseDetailById(id));
    }

    @GetMapping(ApiPath.GET_PAGE)
    @Operation(summary = "Get Course Pagination")
    ResponseEntity<RestAPIResponse<Object>> getPageCourse(@RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
                                         @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize,
                                         @RequestParam(name = "sort_type", defaultValue = "ASC", required = false) Sort.Direction sortType,
                                         @RequestParam(name = "sort_type_date", defaultValue = "ASC", required = false) Sort.Direction sortTypeDate,
                                         @RequestParam(name = "search_key", defaultValue = "", required = false) String searchKey)
    {
        return responseUtil.successResponse(courseService.getPageCourse(pageNumber, pageSize, sortType, sortTypeDate, searchKey));
    }

    @PostMapping(ApiPath.ADD)
    @Operation(summary = "Create new course")
    ResponseEntity<RestAPIResponse<Object>> createCourse(@RequestBody @Valid EditCourseRequest editCourseRequest) {
        CourseDTO courseDTO = new CourseDTO(editCourseRequest);
        courseService.createCourse(courseDTO);
        return responseUtil.successResponse("OK!");
    }

    @PutMapping(ApiPath.EDIT + ApiPath.ID)
    @Operation(summary = "Edit course")
    ResponseEntity<RestAPIResponse<Object>> editCourse(@PathVariable("id") String id, @RequestBody @Valid EditCourseRequest editCourseRequest) {
        CourseDTO courseDTO = new CourseDTO(editCourseRequest);
        courseService.editCourse(id, courseDTO);
        return responseUtil.successResponse("OK!");
    }

    @DeleteMapping(ApiPath.DELETE + ApiPath.ID)
    @Operation(summary = "Delete course")
    ResponseEntity<RestAPIResponse<Object>> deleteCourse(@PathVariable("id") String id) {
        courseService.deleteCourse(id);
        return responseUtil.successResponse("OK!");
    }
}
