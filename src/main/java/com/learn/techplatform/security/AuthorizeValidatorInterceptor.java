package com.learn.techplatform.security;


import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.UserRole;
import com.learn.techplatform.common.exceptions.ApplicationException;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.validations.Validator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class AuthorizeValidatorInterceptor {

    final AuthHelper authHelper;

    public AuthorizeValidatorInterceptor(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    @Before(
            value =
                    "@annotation(com.learn.techplatform.security.AuthorizeValidator)  && @annotation(roles)")
    public void before(JoinPoint caller, AuthorizeValidator roles) {
        // Capture access token from current request
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authToken = httpServletRequest.getHeader(Constant.HEADER_TOKEN);
        if (authToken == null) throw new ApplicationException(RestAPIStatus.UNAUTHORIZED);
        AuthUser authUser = authHelper.loadAuthUserFromToken(authToken, httpServletRequest);
        Validator.notNull(authUser, RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
        // Validate Role
        boolean isValid = isValidate(authUser, roles);
        if (!isValid) throw new ApplicationException(RestAPIStatus.FORBIDDEN, RestStatusMessage.FORBIDDEN);
    }

    public boolean isValidate(AuthUser authUser, AuthorizeValidator types) {
        for (UserRole type : types.value()) {
            if (type == authUser.getUserRole()) return true;
        }

        return false;
    }
}

