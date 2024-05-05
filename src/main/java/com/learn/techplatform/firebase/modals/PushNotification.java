package com.learn.techplatform.firebase.modals;


import com.google.firebase.messaging.Notification;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@Getter
@Setter
@Builder
public class PushNotification {
    private String userId;
    private String title;
    private String body;

}
