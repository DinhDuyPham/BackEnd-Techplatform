package com.learn.techplatform.controllers;


import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.DeviceRequest;
import com.learn.techplatform.firebase.FirebaseService;
import com.learn.techplatform.firebase.modals.PushNotification;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.Device.DeviceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPath.DEVICE_API)
public class DeviceController extends AbstractBaseController {

    @Autowired
    DeviceService deviceService;
    @PostMapping
    ResponseEntity<RestAPIResponse<Object>> updateDevice(@Valid @RequestBody DeviceRequest deviceRequest, @AuthSession AuthUser authUser, HttpServletRequest request)  {
        deviceService.saveDevice(deviceRequest, authUser.getId(), request);
        return responseUtil.successResponse("ok");
    }
}
