package com.learn.techplatform.services.Authentication;

import com.google.gson.Gson;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.SessionType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.*;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.request.ConfirmSignUpRequest;
import com.learn.techplatform.controllers.models.request.EmailRequest;
import com.learn.techplatform.controllers.models.request.LoginRequest;
import com.learn.techplatform.controllers.models.response.AuthResponse;
import com.learn.techplatform.controllers.models.response.TokenResponse;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public TokenResponse signUpUserVerify(UserDTO userDTO, HttpServletRequest request) {
        User userPending = userRepository.findByEmailAndSystemStatusAndUserStatus(
            userDTO.getEmail(),
            SystemStatus.ACTIVE,
            UserStatus.PENDING
        );
        if(userPending != null) {
            userPending.setUserStatus(UserStatus.INACTIVE);
            userPending.setSystemStatus(SystemStatus.INACTIVE);
            userService.save(userPending);
        }
        User userActive = userRepository.findByEmailAndSystemStatusAndUserStatus(
            userDTO.getEmail(),
            SystemStatus.ACTIVE,
            UserStatus.ACTIVE
        );
        Validator.mustNull(userActive, RestAPIStatus.EXISTED, RestStatusMessage.EMAIL_ALREADY_EXISTED);
        User user = userHelper.createUser(userDTO, authHelper.createPasswordHash(userDTO.getPasswordHash()));
        user.setLastIpAddress(AppUtil.getClientIpAddress(request));

        Session session = sessionHelper.createSession(
            user.getId(),
            StringUtils.base64Encode(UniqueID.getRandomOTP()),
            new Date(DateUtil.getUTCNow().getTime() + DateUtil.FIVE_MINUTE).getTime(),
            SessionType.VERIFY_SIGNUP
        );
        sessionService.save(session);
        userService.save(user);
        return TokenResponse.builder()
            .token(session.getId())
            .expireTime(session.getExpireTime())
            .build();
    }

    @Override
    public AuthResponse confirmSignUpUser(ConfirmSignUpRequest confirmSignUpRequest, HttpServletRequest request) {
        Session session = sessionRepository.getByIdAndSystemStatusAndSessionType(
            confirmSignUpRequest.getToken(),
            SystemStatus.ACTIVE,
            SessionType.VERIFY_SIGNUP
        );
        Validator.notNull(session, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        boolean tokenExpire = DateUtil.isBeforeTime(DateUtil.getUTCNow().getTime(), session.getExpireTime());
        Validator.mustTrue(tokenExpire, RestAPIStatus.EXPIRED, RestStatusMessage.EXPIRED_VERIFY_TOKEN);

        Validator.notNull(session.getData(), RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        User userSignUp = userService.getById(session.getUserId());
        Validator.notNull(userSignUp, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        Validator.mustEquals(session.getId(), confirmSignUpRequest.getToken(), RestAPIStatus.NOT_FOUND, RestStatusMessage.TOKEN_ID_NOT_FOUND);
        String passcode = StringUtils.decodeBase64(session.getData());
        log.info("passcode " + passcode);
        Validator.mustEquals(confirmSignUpRequest.getPasscode(), passcode, RestAPIStatus.CONFLICT, RestStatusMessage.PASSCODE_NOT_MATCH);

        userSignUp.setUserStatus(UserStatus.ACTIVE);
        userSignUp.setLastIpAddress(AppUtil.getClientIpAddress(request));
        userSignUp.setLastLogin(DateUtil.getUTCNow());
        userSignUp = userService.save(userSignUp);

        session.setData(StringUtils.base64Encode(session.getData()));
        session.setSystemStatus(SystemStatus.INACTIVE);

        Session sessionAuth = sessionHelper.createSessionAuth(userSignUp.getId());
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        sessionList.add(sessionAuth);
        sessionService.saveAll(sessionList);

        return AuthResponse.builder()
                .accessToken(sessionAuth.getId())
                .expireTime(sessionAuth.getExpireTime())
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request) {
        User user = userRepository.findByEmailAndSystemStatusAndUserStatus(loginRequest.getUsername(), SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
        Validator.mustTrue(authHelper.checkPassword(loginRequest.getPasswordHash(), user), RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);

        user.setLastIpAddress(AppUtil.getClientIpAddress(request));
        user.setLastLogin(DateUtil.getUTCNow());
        userService.save(user);

        Session sessionAuth = sessionHelper.createSessionAuth(user.getId());
        sessionService.save(sessionAuth);

        return AuthResponse.builder()
            .accessToken(sessionAuth.getId())
            .expireTime(sessionAuth.getExpireTime())
            .build();
    }

    @Override
    public void logout(String userId, HttpServletRequest request) {
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        Session session = sessionRepository.getByIdAndSystemStatusAndSessionType(authToken,SystemStatus.ACTIVE, SessionType.PRIMARY);
        if(session != null) {
            if(session.getUserId().equals(userId)) {
                User user = userRepository.findByIdAndSystemStatusAndUserStatus(userId, SystemStatus.ACTIVE, UserStatus.ACTIVE);
                user.setLastIpAddress(AppUtil.getClientIpAddress(request));
                userService.save(user);
            }
            session.setSystemStatus(SystemStatus.INACTIVE);
            sessionService.save(session);
        }
    }

    @Override
    public TokenResponse forgotPassword(EmailRequest emailRequest, AppValueConfigure appValueConfigure, HttpServletRequest request) {
        UserDTO user = userService.getUserByEmail(emailRequest.getEmail());
        Session session = sessionHelper.createSession(
            user.getId(),
            DateUtil.getUTCNow().getTime() + DateUtil.FIVE_MINUTE,
            SessionType.FORGOT_PASSWORD
        );
        return TokenResponse.builder().build();
    }
}
