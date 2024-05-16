package com.learn.techplatform.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.*;
import com.learn.techplatform.common.utils.AppValueConfigure;
import com.learn.techplatform.entities.Device;
import com.learn.techplatform.firebase.modals.PushNotification;
import com.learn.techplatform.services.Device.DeviceService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @Autowired
    DeviceService deviceService;
    @Autowired
    AppValueConfigure appValueConfigure;

    private FirebaseMessaging firebaseMessaging;
    private FirebaseAuth firebaseAuth;


    @PostConstruct
    public void initialize() {
        try {
            InputStream resourceAsStream = FirebaseServiceImpl.class.getClassLoader()
                    .getResourceAsStream(appValueConfigure.firebaseCredentialsFilename);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId(appValueConfigure.firebaseProjectId)
                    .setCredentials(GoogleCredentials.fromStream(resourceAsStream))
                    .build();
            FirebaseApp app = FirebaseApp.initializeApp(options, appValueConfigure.firebaseProjectId);
            this.firebaseMessaging = FirebaseMessaging.getInstance(app);
            this.firebaseAuth = FirebaseAuth.getInstance(app);
            log.info("setup firestore time {}", new Date());
            log.info("setup firebaseAuth {}", firebaseAuth);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error setup Firestore {}", e.getMessage());
        }
    }

    public UserRecord getAuthGoogle(String token) {
        try {
            FirebaseToken decodedToken =  this.firebaseAuth.verifyIdToken(token);
            String uid = decodedToken.getUid();
            UserRecord user = this.firebaseAuth.getUser(uid);
            return user;
        } catch (Exception e) {
            System.out.println("USER >> " + e.getMessage());
        }
        return null;
    }


    public void pushNotification(PushNotification pushNotification) {
        try {
            log.info("pushNotification {}", pushNotification);
            Notification notification = Notification.builder()
                    .setBody(pushNotification.getBody())
                    .setTitle(pushNotification.getTitle())
                    .build();
            List<Device> deviceList = deviceService.getAllDevice(pushNotification.getUserId());
            if(deviceList.isEmpty()) return;
            List<String> fcmTokens = deviceList.stream().map(Device::getFcmToken).filter(fcm -> fcm != null && !fcm.isEmpty()).distinct().toList();
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .addAllTokens(fcmTokens)
                    .putAllData(pushNotification.getData())
                    .build();
           firebaseMessaging.sendMulticast(message);
        } catch (FirebaseMessagingException e ) {
            log.error("sendPnsToTopic()", e);
        }
    }

    public void pushNotification(String userId, String title, String body, Map<String, String> data) {
        this.pushNotification(PushNotification.builder()
                        .title(title)
                        .body(body)
                        .userId(userId)
                        .data(data)
                .build());
    }

    public void pushNotification(String userId, String title, String body) {
        this.pushNotification(PushNotification.builder()
                        .title(title)
                        .body(body)
                        .userId(userId)
                .build());
    }

}
