package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPath.COURSE_API)
public class CourseController extends AbstractBaseController {
}
