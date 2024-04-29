package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.CreateNotificationRequest;
import com.learn.techplatform.controllers.models.request.EditCourseRequest;
import com.learn.techplatform.controllers.models.request.EditNotificationRequest;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.dto_modals.NotificationDTO;
import com.learn.techplatform.services.Notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.NOTIFICATION_API)
public class NotificationController extends AbstractBaseController{
    @Autowired
    NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get Notification Course")
    ResponseEntity<RestAPIResponse<Object>> getAllNotification()
    {
        return responseUtil.successResponse(notificationService.getAll());
    }

    @GetMapping(ApiPath.ID)
    @Operation(summary = "Get Notification By ID")
    ResponseEntity<RestAPIResponse<Object>> getNotificationById(@PathVariable("id") String id)
    {
        return responseUtil.successResponse(notificationService.getById(id));
    }

    @GetMapping(ApiPath.GET_PAGE)
    @Operation(summary = "Get Notification Pagination")
    ResponseEntity<RestAPIResponse<Object>> getPageNotification(@RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
                                                          @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(name = "sort_type", defaultValue = "ASC", required = false) Sort.Direction sortTypeOrder,
                                                          @RequestParam(name = "sort_type_date", defaultValue = "ASC", required = false) Sort.Direction sortTypeDate,
                                                          @RequestParam(name = "search_key", defaultValue = "", required = false) String searchKey)
    {
        return responseUtil.successResponse(notificationService.getPageNotification(pageNumber, pageSize, sortTypeOrder, sortTypeDate, searchKey));
    }

    @PostMapping(ApiPath.ADD)
    @Operation(summary = "Create new notification")
    ResponseEntity<RestAPIResponse<Object>> createNotification(@RequestBody @Valid CreateNotificationRequest createNotificationRequest) {
        NotificationDTO notificationDTO = new NotificationDTO(createNotificationRequest);
        notificationService.createNotification(notificationDTO);
        return responseUtil.successResponse("OK!");
    }

    @PutMapping(ApiPath.EDIT + ApiPath.ID)
    @Operation(summary = "Edit Notification")
    ResponseEntity<RestAPIResponse<Object>> editNotification(@PathVariable("id") String id, @RequestBody @Valid EditNotificationRequest editNotificationRequest) {
        NotificationDTO notificationDTO = new NotificationDTO(editNotificationRequest);
        notificationService.editNotification(id, notificationDTO);
        return responseUtil.successResponse("OK!");
    }

    @DeleteMapping(ApiPath.DELETE + ApiPath.ID)
    @Operation(summary = "Delete Notification")
    ResponseEntity<RestAPIResponse<Object>> deleteNotification(@PathVariable("id") String id) {
        notificationService.deleteNotification(id);
        return responseUtil.successResponse("OK!");
    }
}
