package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiPath.WEBHOOK_API)
public class WebhookController  extends AbstractBaseController {

    @PostMapping(ApiPath.CASSO_EVENT)
    ResponseEntity<RestAPIResponse<Object>> signIn(@RequestBody Object data) {
        log.info("data {}", data);
        return responseUtil.successResponse("ok");
    }
}
