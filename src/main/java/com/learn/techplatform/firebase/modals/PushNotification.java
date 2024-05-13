package com.learn.techplatform.firebase.modals;


import com.google.firebase.messaging.Notification;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
@Builder
public class PushNotification {
    private String userId;
    private String title;
    private String body;
    private Map<String, String> data = new HashMap<>();
}
