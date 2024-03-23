package com.learn.techplatform.services.Authentication;

import com.learn.techplatform.common.utils.AppValueConfigure;
import com.learn.techplatform.controllers.models.request.ConfirmSignUpRequest;
import com.learn.techplatform.controllers.models.request.LoginRequest;
import com.learn.techplatform.controllers.models.response.AuthResponse;
import com.learn.techplatform.controllers.models.response.SignUpUserVerifyResponse;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.services.InterfaceBaseService;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService  {
    SignUpUserVerifyResponse signUpUserVerify(UserDTO userDTO, AppValueConfigure appValueConfigure, HttpServletRequest request);
    AuthResponse confirmSignUpUser(ConfirmSignUpRequest confirmSignUpRequest, AppValueConfigure appValueConfigure, HttpServletRequest request);
    AuthResponse loginUser(LoginRequest loginRequest, AppValueConfigure appValueConfigure, HttpServletRequest request);
    void logout(String userId, HttpServletRequest request);
}
