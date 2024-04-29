package com.learn.techplatform.dto_modals;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.controllers.models.request.CreateNotificationRequest;
import com.learn.techplatform.controllers.models.request.EditNotificationRequest;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class NotificationDTO {
    private String title;
    private String content;
    private String linked;
    private boolean seen;
    private String userId;

    public NotificationDTO(CreateNotificationRequest createNotificationRequest) {
        this.title = createNotificationRequest.getTitle();
        this.content = createNotificationRequest.getContent();
        this.linked = createNotificationRequest.getLinked();
        this.userId = createNotificationRequest.getUserId();
    }

    public NotificationDTO(EditNotificationRequest editNotificationRequest) {
        this.title = editNotificationRequest.getTitle();
        this.content = editNotificationRequest.getContent();
        this.linked = editNotificationRequest.getLinked();
        this.userId = editNotificationRequest.getUserId();
    }
}
