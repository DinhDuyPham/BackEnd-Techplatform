package com.learn.techplatform.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;


@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @PostConstruct
    public void initialize() {
        try {
            URL resource = ClassLoader.getSystemResource("firebase/techplatform-firebase-firebase-adminsdk-qkthw-93f4984425.json");
            File file = new File(resource.toURI());
            FileInputStream serviceAccount =
                    new FileInputStream(file);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserRecord getAuthGoogle(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            return FirebaseAuth.getInstance().getUser(uid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
