package com.learn.techplatform.services.Notification;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.NotificationDTO;
import com.learn.techplatform.entities.Notification;
import com.learn.techplatform.repositories.NotificationRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl extends AbstractBaseService<Notification, String> implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public NotificationServiceImpl(JpaRepository<Notification, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public PagingResponse getPageNotification(int pageNumber, int pageSize, Sort.Direction sortTypeOrder, Sort.Direction sortTypeDate, String searchKey) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(sortTypeOrder, "title"));
        orders.add(new Sort.Order(sortTypeDate, "createdDate"));
        orders.add(new Sort.Order(sortTypeDate, "updatedDate"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        PagingResponse pagingResponse = new PagingResponse(notificationRepository.getPageNotification("%" + searchKey + "%", pageable));
        return pagingResponse;
    }

    @Override
    public void createNotification(NotificationDTO notificationDTO) {
        boolean isNotificationExist = notificationRepository.existsByTitleAndSystemStatus(notificationDTO.getTitle(), SystemStatus.ACTIVE);
        Validator.mustTrue(!isNotificationExist, RestAPIStatus.EXISTED, RestStatusMessage.NOTIFICATION_ALREADY_EXISTED);

        Validator.notNullAndNotEmpty(notificationDTO.getTitle(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_TITLE_FORMAT);
        Validator.notNullAndNotEmpty(notificationDTO.getContent(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_CONTENT_FORMAT);
        Validator.notNullAndNotEmpty(notificationDTO.getLinked(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_LINKED_FORMAT);

        Notification notification = Notification.builder()
                .id(UniqueID.getUUID())
                .title(notificationDTO.getTitle())
                .content(notificationDTO.getContent())
                .linked(notificationDTO.getLinked())
                .seen(false)
                .userId(notificationDTO.getUserId())
                .systemStatus(SystemStatus.ACTIVE)
                .build();

        this.save(notification);
    }

    @Override
    public void editNotification(String id, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.findByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(notification, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOTIFICATION_NOT_FOUND);

        boolean isNotificationExist = notificationRepository.existsByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.mustTrue(!isNotificationExist, RestAPIStatus.EXISTED, RestStatusMessage.NOTIFICATION_ALREADY_EXISTED);
        notification.setTitle(notificationDTO.getTitle());

        if (Validator.checkNull(notificationDTO.getContent()))
            notification.setContent(notification.getContent());
        else
            notification.setContent(notificationDTO.getContent());

        if (Validator.checkNull(notificationDTO.getLinked()))
            notification.setLinked(notification.getLinked());
        else
            notification.setLinked(notificationDTO.getLinked());

        if (Validator.checkNull(notificationDTO.getUserId()))
            notification.setUserId(notification.getUserId());
        else
            notification.setUserId(notificationDTO.getUserId());

        this.save(notification);
    }

    @Override
    public void deleteNotification(String id) {
        Notification notification = notificationRepository.findByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(notification, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOTIFICATION_NOT_FOUND);
        notification.setSystemStatus(SystemStatus.INACTIVE);
        this.save(notification);
    }
}
