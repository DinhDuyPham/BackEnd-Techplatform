package com.learn.techplatform.helper;


import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.entities.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {

    public Session createSession(String data, long expireTime) {
        return Session.builder()
                .id(UniqueID.getUUID())
                .data(data)
                .systemStatus(SystemStatus.ACTIVE)
                .expireTime(expireTime)
                .build();
    }
}
