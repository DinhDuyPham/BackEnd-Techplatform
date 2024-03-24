package com.learn.techplatform.helper;


import com.learn.techplatform.common.enums.SessionType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.utils.AppValueConfigure;
import com.learn.techplatform.common.utils.DateUtil;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.entities.Session;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {

    @Autowired
    AppValueConfigure appValueConfigure;

    public Session createSession(String userId, long expireTime) {
        return Session.builder()
                .id(UniqueID.getUUID())
                .userId(userId)
                .systemStatus(SystemStatus.ACTIVE)
                .expireTime(expireTime)
                .sessionType(SessionType.PRIMARY)
                .build();
    }

    public Session createSession(String userId, long expireTime, SessionType sessionType) {
        return Session.builder()
                .id(UniqueID.getUUID())
                .userId(userId)
                .systemStatus(SystemStatus.ACTIVE)
                .expireTime(expireTime)
                .sessionType(sessionType)
                .build();
    }

    public Session createSession(String userId, String data, long expireTime, SessionType sessionType) {
        return Session.builder()
                .id(UniqueID.getUUID())
                .userId(userId)
                .data(data)
                .systemStatus(SystemStatus.ACTIVE)
                .expireTime(expireTime)
                .sessionType(sessionType)
                .build();
    }

    public Session createSessionAuth(String userId) {
        return this.createSession(userId, DateUtil.getUTCNow().getTime() + appValueConfigure.JWT_EXPIRATION);
    }

    public boolean isExpirySession(Session session) {
        return DateUtil.isBeforeTime(DateUtil.getUTCNow().getTime(), session.getExpireTime());
    }
}
