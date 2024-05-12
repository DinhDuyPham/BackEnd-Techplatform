package com.learn.techplatform.firebase.modals;

import com.learn.techplatform.common.enums.OrderHistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoursePaymentNotification {
    private PushNotificationType type;
    private String courseId;
    private OrderHistoryStatus status;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type.name());
        map.put("courseId", courseId);
        map.put("status", status.name());
        return map;
    }
}
