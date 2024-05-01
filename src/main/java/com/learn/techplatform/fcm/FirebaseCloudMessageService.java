package com.learn.techplatform.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.learn.techplatform.common.utils.AppValueConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class FirebaseCloudMessageService {
    private final AppValueConfigure valueConfigure;
    private FirebaseMessaging firebaseMessaging;

    public FirebaseCloudMessageService(AppValueConfigure valueConfigure){
        this.valueConfigure = valueConfigure;
    }

//    private setupFirebaseMessaging() {
//        if (firebaseMessaging == null){
//            try {
//                InputStream resourceAsStream = FirestoreService.class.getClassLoader()
//                        .getResourceAsStream(configureValues.FIRESTORE_CREDENTIALS_FILENAME);
//                GoogleCredentials googleCredentials = GoogleCredentials.fromStream(resourceAsStream);
//                FirebaseOptions firebaseOptions = FirebaseOptions
//                        .builder()
//                        .setCredentials(googleCredentials)
//                        .build();
//                FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, configureValues.FIRESTORE_PROJECT_ID);
//                firebaseMessaging = FirebaseMessaging.getInstance(app);
//                log.info("setup firestore time {}", new Date());
//            }catch (Exception exception) {
//                log.error("Error setup Firestore {}", exception);
//                Sentry.captureException(exception);
//            }
//    }
}
