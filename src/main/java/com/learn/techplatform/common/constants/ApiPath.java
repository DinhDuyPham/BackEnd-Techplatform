package com.learn.techplatform.common.constants;

public interface ApiPath {
    String BASE_API_URL = "/api";

    String AUTHENTICATE_API = BASE_API_URL + "/auth";
    String USER_API = BASE_API_URL + "/user";
    String PRODUCT_API = BASE_API_URL + "/product";
    String ROLE_API = BASE_API_URL + "/role";
    String OTP_API = BASE_API_URL + "/otp";


    // common
    String ID = "/{id}";
    String DETAIL = "/detail";
    String ADD = "/add";
    String EDIT = "/edit";
    String DELETE = "/delete";
    String CANCEL = "/cancel";
    String GET_PAGE = "/page";
    String EXPORT = "/export";
    String VERIFY_TOKEN = "/{verify-token}";

    // Authenticate APIs
    String LOGIN = "/login";
    String LOGOUT = "/logout";
    String SIGN_UP = "/sign-up";
    String RESET_PASSWORD = "/reset-password";
    String AuthInFo = "/auth-info";
    String GOOGLE_LOGIN = "/google";
    String CHECK_PHONE_NUMBER_SIGNUP = "/check-phone-signup";
    String VERIFY_EMAIL = "/verify-email";
    String EMAIL_SIGNUP = "/email-signup";
    String SIGNUP_VERIFY = "/signup-verify";
    String FORGOT_PASSWORD = "/forgot-password";
    String REFRESH_TOKEN = "/refresh-token";
}
