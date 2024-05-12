package com.learn.techplatform.casso_webhook_recerver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.techplatform.casso_webhook_recerver.modals.CassoErrorStatus;
import com.learn.techplatform.casso_webhook_recerver.modals.CassoKeys;
import com.learn.techplatform.casso_webhook_recerver.modals.CassoResponse;
import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.enums.OrderHistoryStatus;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.ResponseUtil;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.AbstractBaseController;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.entities.CourseHistory;
import com.learn.techplatform.entities.OrderHistory;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.fcm.FirebaseCloudMessageService;
import com.learn.techplatform.firebase.FirebaseService;
import com.learn.techplatform.firebase.modals.CoursePaymentNotification;
import com.learn.techplatform.firebase.modals.PushNotification;
import com.learn.techplatform.firebase.modals.PushNotificationType;
import com.learn.techplatform.services.Course.CourseService;
import com.learn.techplatform.services.CourseHistory.CourseHistoryService;
import com.learn.techplatform.services.OrderHistory.OrderHistoryService;
import com.learn.techplatform.services.User.UserService;
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
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    CourseHistoryService courseHistoryService;
    @Autowired
    OrderHistoryService orderHistoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FirebaseService firebaseService;

    @PostMapping
    ResponseEntity<RestAPIResponse<Object>> cassoWebhookHandler(@RequestBody Object data) {
        OrderHistory orderHistory =  OrderHistory.builder()
                .id(UniqueID.getUUID())
                .systemStatus(SystemStatus.ACTIVE)
                .status(OrderHistoryStatus.SUCCESS)
                .build();
        User user = null;
        Course course = null;
        try {
            CassoResponse cassoResponse = objectMapper.convertValue(data, CassoResponse.class);
            if(cassoResponse.error != CassoErrorStatus.SUCCESS.getStatusCode()) {
                orderHistoryService.save(orderHistory);
                return responseUtil.successResponse("ok");
            }
            CassoKeys cassoKeys = getKeyData(cassoResponse);
            course = courseService.getCourseByCode(cassoKeys.getCourseCode());
            user = userService.getByUsername(cassoKeys.getUsername());
            if(user == null || course == null) {
                orderHistoryService.save(orderHistory);
                return responseUtil.successResponse("ok");
            }
            orderHistory.setCourseId(course.getId());
            orderHistory.setUserId(user.getId());

            CourseHistory courseHistory = CourseHistory.builder()
                    .id(UniqueID.getUUID())
                    .userId(user.getId())
                    .courseId(course.getId())
                    .systemStatus(SystemStatus.ACTIVE)
                    .build();
            courseHistoryService.save(courseHistory);
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            assert user != null;
            CoursePaymentNotification dataPushNotify = CoursePaymentNotification.builder()
                    .type(PushNotificationType.PAYMENT_COURSE)
                    .status(OrderHistoryStatus.FAILED)
                    .courseId(course.getId())
                    .build();
            firebaseService.pushNotification(PushNotification.builder()
                    .userId(user.getId())
                    .body("Mua không thành công")
                    .data(dataPushNotify.toMap())
                    .build());
            orderHistory.setStatus(OrderHistoryStatus.FAILED);
            orderHistoryService.save(orderHistory);
            return responseUtil.successResponse("ok");
        }
        orderHistoryService.save(orderHistory);
        CoursePaymentNotification dataPushNotify = CoursePaymentNotification.builder()
                .type(PushNotificationType.PAYMENT_COURSE)
                .status(OrderHistoryStatus.SUCCESS)
                .courseId(course.getId())
                .build();
        firebaseService.pushNotification(PushNotification.builder()
                .userId(user.getId())
                .body(user.getFirstName() + " đã mua thành công khóa học " + course.getTitle())
                .title("Mua khóa học thành công")
                .data(dataPushNotify.toMap())
                .build());
        return responseUtil.successResponse("ok");
    }


    private CassoKeys getKeyData(CassoResponse cassoResponse) {
        CassoKeys cassoKeys = new CassoKeys();
        String description = cassoResponse.data.get(0).description;
        String[] parts = description.split("\\s+");
        cassoKeys.setCourseCode(parts[0]);
        cassoKeys.setUsername(parts[1]);
        return cassoKeys;
    }
}
