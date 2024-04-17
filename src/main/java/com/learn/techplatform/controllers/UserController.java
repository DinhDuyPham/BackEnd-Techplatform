package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.services.User.UserService;
import com.learn.techplatform.services.User.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPath.USER_API)
public class UserController extends AbstractBaseController {
    @Autowired
    UserService userService;

    @GetMapping
    ResponseEntity<RestAPIResponse<Object>> getUser() {
        return this.responseUtil.successResponse("hello");
    }
}
