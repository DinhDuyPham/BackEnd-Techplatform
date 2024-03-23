package com.learn.techplatform.services.Authentication;

import com.google.gson.Gson;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.*;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.request.ConfirmSignUpRequest;
import com.learn.techplatform.controllers.models.request.LoginRequest;
import com.learn.techplatform.controllers.models.response.AuthResponse;
import com.learn.techplatform.controllers.models.response.SignUpUserVerifyResponse;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.Session;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.helper.SessionHelper;
import com.learn.techplatform.helper.UserHelper;
import com.learn.techplatform.models.Session.SignUpVerifySession;
import com.learn.techplatform.repositories.SessionRepository;
import com.learn.techplatform.repositories.UserRepository;
import com.learn.techplatform.secrity.AuthHelper;
import com.learn.techplatform.services.Session.SessionService;
import com.learn.techplatform.services.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SessionService sessionService;

    @Autowired
    UserHelper userHelper;
    @Autowired
    AuthHelper authHelper;
    @Autowired
    SessionHelper sessionHelper;

    @Override
    public SignUpUserVerifyResponse signUpUserVerify(UserDTO userDTO, AppValueConfigure appValueConfigure, HttpServletRequest request) {
        User userPending = userRepository.findByEmailAndSystemStatusAndUserStatus(userDTO.getEmail(), SystemStatus.ACTIVE, UserStatus.PENDING);
        if(userPending != null) {
            userPending.setUserStatus(UserStatus.INACTIVE);
            userPending.setSystemStatus(SystemStatus.INACTIVE);
            userService.save(userPending);
        }
        User userActive = userRepository.findByEmailAndSystemStatusAndUserStatus(userDTO.getEmail(), SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.mustNull(userActive, RestAPIStatus.EXISTED, RestStatusMessage.EMAIL_ALREADY_EXISTED);
        User user = userHelper.createUser(userDTO, authHelper.createPasswordHash(userDTO.getPasswordHash()));
        user.setLastIpAddress(AppUtil.getClientIpAddress(request));

        SignUpVerifySession data = new SignUpVerifySession(user.getId(), UniqueID.getRandomOTP());
        Session session = sessionHelper.createSession(new Gson().toJson(data), new Date(DateUtil.getUTCNow().getTime() + DateUtil.FIVE_MINUTE).getTime());
        sessionService.save(session);
        userService.save(user);
        return SignUpUserVerifyResponse.builder()
                .token(session.getId())
                .expireTime(session.getExpireTime())
                .build();
    }

    @Override
    public AuthResponse confirmSignUpUser(ConfirmSignUpRequest confirmSignUpRequest, AppValueConfigure appValueConfigure, HttpServletRequest request) {
        Session session = sessionRepository.getByIdAndSystemStatus(confirmSignUpRequest.getToken(), SystemStatus.ACTIVE);
        Validator.notNull(session, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        boolean tokenExpire = DateUtil.isBeforeTime(DateUtil.getUTCNow().getTime(), session.getExpireTime());
        Validator.mustTrue(tokenExpire, RestAPIStatus.EXPIRED, RestStatusMessage.EXPIRED_VERIFY_TOKEN);
        SignUpVerifySession signUpVerifySession = new Gson().fromJson(session.getData(), SignUpVerifySession.class);
        Validator.notNull(signUpVerifySession, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        User userSignUp = userService.getById(signUpVerifySession.getUserId());
        Validator.notNull(userSignUp, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        Validator.mustEquals(session.getId(), confirmSignUpRequest.getToken(), RestAPIStatus.NOT_FOUND, RestStatusMessage.TOKEN_ID_NOT_FOUND);
        Validator.mustEquals(confirmSignUpRequest.getPasscode(),signUpVerifySession.getPasscode(), RestAPIStatus.CONFLICT, RestStatusMessage.PASSCODE_NOT_MATCH);

        userSignUp.setUserStatus(UserStatus.ACTIVE);
        userSignUp.setLastIpAddress(AppUtil.getClientIpAddress(request));
        userSignUp.setLastLogin(DateUtil.getUTCNow());
        userSignUp = userService.save(userSignUp);

        session.setData(StringUtils.base64Encode(session.getData()));
        session.setSystemStatus(SystemStatus.INACTIVE);
        sessionService.save(session);

        Session sessionAuth = sessionHelper.createSession(userSignUp.getId(), DateUtil.getUTCNow().getTime() + appValueConfigure.JWT_EXPIRATION);
        sessionService.save(sessionAuth);

        return AuthResponse.builder()
                .accessToken(sessionAuth.getId())
                .expireTime(sessionAuth.getExpireTime())
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest, AppValueConfigure appValueConfigure, HttpServletRequest request) {
        User user = userRepository.findByEmailAndSystemStatusAndUserStatus(loginRequest.getUsername(), SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
        Validator.mustTrue(authHelper.checkPassword(loginRequest.getPasswordHash(), user), RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);

        user.setLastIpAddress(AppUtil.getClientIpAddress(request));
        user.setLastLogin(DateUtil.getUTCNow());
        userService.save(user);

        Session sessionAuth = sessionHelper.createSession(user.getId(), DateUtil.getUTCNow().getTime() + appValueConfigure.JWT_EXPIRATION);
        sessionService.save(sessionAuth);


        return AuthResponse.builder()
                .accessToken(sessionAuth.getId())
                .expireTime(sessionAuth.getExpireTime())
                .build();
    }

    @Override
    public void logout(String userId, HttpServletRequest request) {
        User user = userRepository.findByIdAndSystemStatusAndUserStatus(userId, SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.NOT_FOUND, RestStatusMessage.USER_NOT_FOUND);
        user.setLastIpAddress(AppUtil.getClientIpAddress(request));
        userService.save(user);
        Session session = sessionService.getByDataAndSystemStatus(user.getId(),SystemStatus.ACTIVE);
        Validator.notNull(session, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        session.setSystemStatus(SystemStatus.INACTIVE);
        String encode = StringUtils.base64Encode(user.getId());
        session.setData(encode);
        log.info("Decode >> " + StringUtils.decodeBase64(encode));
        sessionService.save(session);
    }
}
