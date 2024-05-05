package com.learn.techplatform.firebase;

import com.google.firebase.auth.UserRecord;
import com.learn.techplatform.firebase.modals.PushNotification;

public interface FirebaseService {
    UserRecord getAuthGoogle(String token);
    void pushNotification(PushNotification notification);
}
