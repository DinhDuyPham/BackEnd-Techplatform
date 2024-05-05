package com.learn.techplatform.services.Authentication;

import com.google.firebase.auth.UserRecord;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.SessionType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.*;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.request.*;
import com.learn.techplatform.controllers.models.response.AuthResponse;
import com.learn.techplatform.controllers.models.response.TokenResponse;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.Session;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.firebase.FirebaseService;
import com.learn.techplatform.firebase.modals.PushNotification;
import com.learn.techplatform.helper.SessionHelper;
import com.learn.techplatform.helper.UserHelper;
import com.learn.techplatform.repositories.SessionRepository;
import com.learn.techplatform.repositories.UserRepository;
import com.learn.techplatform.security.AuthHelper;
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
    FirebaseService firebaseService;

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
        if (userPending != null) {
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
        Validator.mustTrue(sessionHelper.isExpirySession(session), RestAPIStatus.EXPIRED, RestStatusMessage.EXPIRED_VERIFY_TOKEN);

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
    public AuthResponse googleLoginUser(GoogleLoginRequest loginRequest, HttpServletRequest request, AppValueConfigure appValueConfigure) {
        UserRecord userRecord = firebaseService.getAuthGoogle(loginRequest.getToken());
        Validator.notNull(userRecord, RestAPIStatus.INVALID_CREDENTIAL, RestStatusMessage.INVALID_AUTHENTICATE_CREDENTIAL);
        User user = userRepository.findByEmailAndSystemStatusAndUserStatus(userRecord.getEmail(), SystemStatus.ACTIVE, UserStatus.ACTIVE);
        if (user == null) {
            user = userHelper.createUser(userRecord);
        }
        user = userService.save(user);
        Session sessionAuth = sessionHelper.createSession(user.getId(), DateUtil.getUTCNow().getTime() + appValueConfigure.JWT_EXPIRATION, SessionType.GOOGLE_LOGIN);
        firebaseService.pushNotification(PushNotification.builder()
                        .userId(user.getId())
                        .title("Hello")
                        .body("chao")
                .build());
        sessionService.save(sessionAuth);
        return AuthResponse.builder()
                .accessToken(sessionAuth.getId())
                .expireTime(sessionAuth.getExpireTime())
                .build();
    }

    @Override
    public void logout(String userId, HttpServletRequest request) {
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        Session session = sessionRepository.getByIdAndSystemStatusAndSessionType(authToken, SystemStatus.ACTIVE, SessionType.PRIMARY);
        if (session != null) {
            if (session.getUserId().equals(userId)) {
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
        boolean isUserExist = user != null;
        Session session = sessionHelper.createSession(
                !isUserExist ? "" : user.getId(),
                StringUtils.base64Encode(UniqueID.getRandomOTP()),
                DateUtil.getUTCNow().getTime() + DateUtil.FIVE_MINUTE,
                SessionType.FORGOT_PASSWORD
        );
        if (isUserExist) {
            sessionService.save(session);
        }
        return TokenResponse.builder()
                .token(!isUserExist ? UniqueID.getUUID() : session.getId())
                .expireTime(session.getExpireTime())
                .build();
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest, HttpServletRequest request) {
        Session session = sessionRepository.getByIdAndSystemStatusAndSessionType(
                resetPasswordRequest.getToken(),
                SystemStatus.ACTIVE,
                SessionType.FORGOT_PASSWORD
        );
        Validator.notNull(session, RestAPIStatus.NOT_FOUND, RestStatusMessage.SESSION_NOT_FOUND);
        Validator.mustTrue(sessionHelper.isExpirySession(session), RestAPIStatus.BAD_REQUEST, RestStatusMessage.BAD_REQUEST);
        String otpDecode = StringUtils.decodeBase64(session.getData());
        log.info("otpDecode " + otpDecode);
        Validator.mustEquals(otpDecode, resetPasswordRequest.getOtp(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.BAD_REQUEST);
        // Check password match
        Validator.mustEquals(
                resetPasswordRequest.getPassword(),
                resetPasswordRequest.getConfirmPassword(),
                RestAPIStatus.CONFLICT,
                RestStatusMessage.CONFIRM_PASSWORD_DOES_NOT_MATCH
        );

        User user = userRepository.findByIdAndSystemStatusAndUserStatus(
                session.getUserId(),
                SystemStatus.ACTIVE,
                UserStatus.ACTIVE
        );
        Validator.notNull(user, RestAPIStatus.BAD_REQUEST, RestStatusMessage.BAD_REQUEST);
        user.setLastIpAddress(AppUtil.getClientIpAddress(request));
        String resetPassword = authHelper.generatePasswordHash(resetPasswordRequest.getConfirmPassword(), user);
        user.setPasswordHash(resetPassword);
        userService.save(user);
        session.setSystemStatus(SystemStatus.INACTIVE);
        sessionService.save(session);
    }
}
