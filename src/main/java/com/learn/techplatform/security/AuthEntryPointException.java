package com.learn.techplatform.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
import java.io.Serializable;
@Slf4j
@Component
@RestControllerAdvice
public class AuthEntryPointException implements AuthenticationEntryPoint, Serializable {

    final private ObjectMapper objectMapper;

    public AuthEntryPointException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        log.error("authException" + authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new RestAPIResponse<>(RestAPIStatus.UNAUTHORIZED, null, RestStatusMessage.UNAUTHORIZED.name())));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new RestAPIResponse<>(RestAPIStatus.FORBIDDEN, null, RestStatusMessage.FORBIDDEN.name())));
    }

    @ExceptionHandler(value = {MultipartException.class})
    public void handleFileUploadingError(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response
                .getWriter()
                .write(objectMapper.writeValueAsString(new RestAPIResponse(RestAPIStatus.BAD_REQUEST, null, RestStatusMessage.BAD_REQUEST.name())));
    }



}
