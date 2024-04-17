package com.learn.techplatform.security;


import com.learn.techplatform.common.exceptions.ApplicationException;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.validations.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class AuthSessionResolver implements HandlerMethodArgumentResolver {

    public AuthSessionResolver() {}

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthSession.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        AuthUser authUser = null;
        AuthSession authSession = parameter.getParameterAnnotation(AuthSession.class);
        if (authSession != null) {
            try {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                authUser = (AuthUser) principal;

                Validator.notNull(authUser, RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
            } catch (Exception e) {
                log.error("AuthSessionResolver " + e.getMessage());
                throw new ApplicationException(RestAPIStatus.UNAUTHORIZED, RestStatusMessage.UNAUTHORIZED);
            }
        }
        return authUser;
    }
}


