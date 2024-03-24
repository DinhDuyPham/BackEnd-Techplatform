package com.learn.techplatform.firebase;

import com.google.firebase.auth.UserRecord;

public interface FirebaseService {
    UserRecord getAuthGoogle(String token);

}
