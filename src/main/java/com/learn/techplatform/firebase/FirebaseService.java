package com.learn.techplatform.firebase;

import com.google.firebase.auth.UserRecord;
import com.learn.techplatform.firebase.modals.PushNotification;

import java.util.Map;

public interface FirebaseService {
    UserRecord getAuthGoogle(String token);
    void pushNotification(PushNotification notification);
    void pushNotification(String userId, String title, String body);
    void pushNotification(String userId, String title, String body, Map<String, String> data);

}
