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
import java.net.URL;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @Autowired
    DeviceService deviceService;
    @Autowired
    AppValueConfigure appValueConfigure;

    private FirebaseMessaging firebaseMessaging;


    @PostConstruct
    public void initialize() {
        try {
            URL resource = ClassLoader.getSystemResource("firebase/techplatform-firebase-firebase-adminsdk-qkthw-93f4984425.json");
            File file = new File(resource.toURI());
            FileInputStream serviceAccount =
                    new FileInputStream(file);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId( appValueConfigure.firebaseProjectId)
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp app = null;
            if(FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(options);
            }else {
                app = FirebaseApp.initializeApp(options);
            }
            this.firebaseMessaging = FirebaseMessaging.getInstance(app);
            log.info("setup firestore time {}", new Date());

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error setup Firestore {}", e.getMessage());
        }
    }

    public UserRecord getAuthGoogle(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            UserRecord user = FirebaseAuth.getInstance().getUser(uid);
            return user;
        } catch (Exception e) {
            System.out.println("USER >> " + e.getMessage());
        }
        return null;
    }


    public void pushNotification(PushNotification pushNotification) {
        try {
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

}
