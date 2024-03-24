package com.learn.techplatform.secrity;


import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.AppUtil;
import com.learn.techplatform.common.utils.DateUtil;
import com.learn.techplatform.common.utils.PasswordHash;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.entities.Session;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.services.Session.SessionService;
import com.learn.techplatform.services.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class AuthHelper {

    final PasswordEncoder passwordEncoder;
    final UserService userService;
    final SessionService sessionService;

    public AuthHelper(PasswordEncoder passwordEncoder, UserService userService, SessionService sessionService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public AuthUser loadAuthUserFromToken(String authToken, HttpServletRequest request) {
        Session session = sessionService.getByIdAndSystemStatus(authToken, SystemStatus.ACTIVE);
        Validator.notNull(session, RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
        Validator.mustTrue(DateUtil.isBeforeTime(DateUtil.getUTCNow().getTime(), session.getExpireTime()), RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
        User user = userService.getById(session.getUserId());
        Validator.notNull(user, RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
        user = updateLastIpAddress(user, request);
        return new AuthUser(user);
    }

    public boolean checkPassword(String passwordHash, User user) {
        return passwordEncoder.matches(
                passwordHash.trim().concat(user.getPasswordSalt()), user.getPasswordHash());
    }

    public String generatePasswordHash(String passwordHash, User user) {
        return passwordEncoder.encode(passwordHash.trim().concat(user.getPasswordSalt()));
    }

    public PasswordHash createPasswordHash(String passwordHashReq) {
        String passwordSalt = AppUtil.generateSalt();
        String passwordHash = passwordEncoder.encode(passwordHashReq.trim().concat(passwordSalt));
        return PasswordHash.builder()
                .passwordHash(passwordHash)
                .passwordSalt(passwordSalt)
                .build();
    }

    @Async
    protected synchronized User updateLastIpAddress(User user, HttpServletRequest request) {
        user.setLastIpAddress(AppUtil.getClientIpAddress(request));
        return userService.save(user);
    }


}

