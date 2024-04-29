package com.learn.techplatform.services.Notification;

import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.NotificationDTO;
import com.learn.techplatform.entities.Notification;
import com.learn.techplatform.services.InterfaceBaseService;
import org.springframework.data.domain.Sort;

public interface NotificationService extends InterfaceBaseService<Notification, String> {
    PagingResponse getPageNotification(int pageNumber, int pageSize,
                                       Sort.Direction sortTypeOrder, Sort.Direction sortTypeDate, String searchKey);
    void createNotification(NotificationDTO notificationDTO);
    void editNotification(String id, NotificationDTO notificationDTO);
    void deleteNotification(String id);
}
