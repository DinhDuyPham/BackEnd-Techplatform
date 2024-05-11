package com.learn.techplatform.casso_webhook_recerver;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.ResponseUtil;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.AbstractBaseController;
import com.learn.techplatform.fcm.FirebaseCloudMessageService;
import com.learn.techplatform.firebase.FirebaseService;
import com.learn.techplatform.firebase.modals.PushNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPath.CASSO_WEBHOOK_HANDLER)
public class CassoWebhookController {
    @Autowired
    ResponseUtil responseUtil;

    @Autowired
    FirebaseService firebaseService;

    @PostMapping
    ResponseEntity<RestAPIResponse<Object>> cassoWebhookHandler(@RequestBody Object data) {
        log.info("data {}", data);
        firebaseService.pushNotification(PushNotification.builder()
                        .userId("1f06e0de36034da6ae145da7deaa2555")
                        .title("SUCCESS")
                        .body("NICE")

                .build());
        return responseUtil.successResponse("ok");
    }
}
