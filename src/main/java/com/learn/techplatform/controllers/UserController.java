package com.learn.techplatform.controllers;

import com.learn.techplatform.cloudinary.CloudinaryService;
import com.learn.techplatform.cloudinary.modal.CloudinaryUploadResponse;
import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.DateUtil;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.request.EditUserRequest;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.helper.UserHelper;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RestController
@RequestMapping(ApiPath.USER_API)
public class UserController extends AbstractBaseController {
    @Autowired
    UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    UserHelper userHelper;

    @Value("${cloudinary.folder.avatar}")
    public String folderAvatar;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Edit user information")
    ResponseEntity<RestAPIResponse<Object>> editUserInfo(
            @AuthSession() AuthUser authUser,
            @RequestPart(name = "content-data", required = false) @Valid EditUserRequest editUserRequest,
            @RequestPart(name = "file", required = false) MultipartFile fileImage
    ) {
        User user = userService.getByIdAndUserStatus(authUser.getId(), UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.NOT_FOUND, RestStatusMessage.USER_NOT_FOUND);
        Long dateOfBirth = editUserRequest.getDateOfBirth() == null ? null : DateUtil.convertStringDateToLong(editUserRequest.getDateOfBirth());
        CloudinaryUploadResponse response = cloudinaryService.uploadImage(fileImage, folderAvatar);
        String imageUrl = response.getSecureUrl();
        String prefixUrlImage =
                appValueConfigure.cloudinaryUrl
                        + appValueConfigure.cloudinaryCloudName
                        + appValueConfigure.cloudinaryPathImageUpload
                        + "/";
        if (imageUrl.startsWith(prefixUrlImage)) {
            imageUrl = imageUrl.replace(prefixUrlImage, "");
        }
        String imagePublicId = response.getPublicId();
        userHelper.editUser(user, editUserRequest, imageUrl, imagePublicId, dateOfBirth);
        userService.save(user);
        return responseUtil.successResponse("OK!");
    }

    @DeleteMapping(ApiPath.DELETE)
    @Operation(summary = "Set account into inactive status")
    ResponseEntity<RestAPIResponse<Object>> deleteAccount(@Parameter(hidden = true) @AuthSession() AuthUser authUser) {
        userService.deleteAccount(authUser.getId());
        return responseUtil.successResponse("OK!");
    }

    @PostMapping(ApiPath.SURVEY)
    @Operation(summary = "Get survey")
    ResponseEntity<RestAPIResponse<Object>> userSurvey(@AuthSession() AuthUser authUser) {
        userService.newUserSurvey(authUser.getId());
        return responseUtil.successResponse("OK!");
    }
}
