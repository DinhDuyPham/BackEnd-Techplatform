package com.learn.techplatform.common.restfullApi;

public enum RestStatusMessage {
    UNAUTHORIZED, FORBIDDEN, INVALID_AUTHENTICATE_CREDENTIAL, INVALID_EMAIL_FORMAT, EMAIL_ALREADY_EXISTED, BAD_REQUEST,
    USERNAME_ALREADY_EXISTED, USER_NOT_FOUND, PASSWORD_DOES_NOT_MATCH, SESSION_NOT_FOUND, CONFIRM_PASSWORD_DOES_NOT_MATCH,
    THE_LINK_IS_EXPIRED, CURRENT_PASSWORD_IS_INCORRECT, NEW_PASSWORD_MUST_BE_DIFFERENT_FROM_CURRENT_PASSWORD, VERIFY_OTP,
    ADMIN_NOT_CREATE, INVALID_SOCIAL_TYPE, CUSTOMER_NOT_FOUND, CONTACT_NOT_FOUND, APPLE_ID_NOT_FOUND, TOKEN_ID_NOT_FOUND,
    EMAIL_NOT_SUPPORTED_FORGOT_PASSWORD, SUBSCRIPTION_PACKAGE_NOT_FOUND, INVALID_FACEBOOK_TOKEN, INVALID_PAGE_NUMBER_OR_PAGE_SIZE,
    WORK_SPACE_SETTING_NOT_FOUND, WORKSPACE_NOT_FOUND, INVALID_USERNAME_FORMAT, NOTICE_NOT_FOUND, WORKSPACE_SETTING_NOT_FOUND,
    INVALID_DATE_FORMAT, START_TIME_DATE_FROM_IS_GREATER_THAN_START_TIME_DATE_TO,
    END_TIME_DATE_FROM_IS_GREATER_THAN_END_TIME_DATE_TO, WORKSPACE_USER_NOT_FOUND, FORBIDDEN_ACCESS_DENIED, EMAIL_IS_NOT_SHARED,
    START_TIME_IS_GREATER_THAN_END_TIME, CAN_ONLY_DELETE_YOUR_OWN_INFORMATION, WORKSPACE_ADMIN_IS_NULL, MESSAGE_TEMPLATE_NOT_FOUND,
    WORKSPACE_USER_ALREADY_EXISTED, PASSWORD_CANNOT_BE_NULL, MINIMUM_CONVERSATION_LOCK_TIME_IS_1, WORKSPACE_NOT_NULL,
    INVALID_FILE_SIZE, INVALID_UPLOAD_IMAGE, INTERNAL_SERVER_ERROR, INVALID_VERIFIED_CODE, EXPIRED_VERIFIED_CODE,
    MULTIPLE_REQUEST_CODE, YOU_DONT_HAVE_PERMISSION_TO_CREATE_NOTICE, YOU_DONT_HAVE_PERMISSION_TO_UPDATE_NOTICE,
    YOU_DONT_HAVE_PERMISSION_TO_DELETE_NOTICE, BROADCAST_LIST_NOT_FOUND, OTP_IS_INCORRECT, OTP_CANNOT_BE_USED_ANYMORE,
    EMAIL_HAS_BEEN_RECEIVED_OTP_NOTFOUND, OTP_HAS_EXPIRED, WRONG_PASSWORD, PASSWORD_NOT_MATCH,PASSCODE_NOT_MATCH, PHONE_NUMBER_ALREADY_EXISTED,
    INVALID_PAGING_REQUEST, CANNOT_FORGOT_PASSWORD, EMAIL_EXISTED, FORGOT_PASSWORD_EXPIRED, PARAMETERS_ARE_NOT_PASSED,
    INVALID_SESSION_TYPE, INVALID_PHONE_NUMBER_FORMAT, PHONE_OR_EMAIL_ALREADY_EXISTED, EXPIRED_VERIFY_TOKEN, USER_ALREADY_EXISTED,
    NO_RESULT, INVALID_FORMAT_OR_SIZE, UPLOAD_IMAGE_FAILED
}

