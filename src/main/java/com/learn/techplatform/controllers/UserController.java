package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.EditUserRequest;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(ApiPath.ADD)
    @Operation(summary = "Register user")
    ResponseEntity<RestAPIResponse<Object>> signUpUser() {
        return responseUtil.successResponse("OK!");
    }

    @PutMapping(ApiPath.EDIT)
    @Operation(summary = "Edit user information")
    ResponseEntity<RestAPIResponse<Object>> editUserInfo(@Parameter(hidden = true) @AuthSession() AuthUser user, @RequestBody @Valid EditUserRequest editUserRequest) {
        userService.editUserInfo(user.getId(), editUserRequest);
        return responseUtil.successResponse("OK!");
    }

    @DeleteMapping(ApiPath.DELETE)
    @Operation(summary = "Set account into inactive status")
    ResponseEntity<RestAPIResponse<Object>> deleteAccount(@Parameter(hidden = true) @AuthSession() AuthUser authUser) {
        userService.deleteAccount(authUser.getId());
        return responseUtil.successResponse("OK!");
    }
}
