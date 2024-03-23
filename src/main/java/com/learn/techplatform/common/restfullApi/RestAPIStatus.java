package com.learn.techplatform.common.restfullApi;


public enum RestAPIStatus {
    OK(200, "OK"),
    NO_RESULT(201, "No more result."),
    FAIL(202, "Fail"),
    NON_AUTHORITATIVE_INFORMATION(203, ""),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized or Access Token is expired"),
    INVALID_AUTHENTICATE_CREDENTIAL(402, "Invalid authenticated credential"),
    FORBIDDEN(403, "Forbidden! Access denied"),
    NOT_FOUND(404, "Not Found"),
    EXISTED(405, "Already existed"),
    BAD_PARAMS(406, "There is some invalid data"),
    EXPIRED(407, "Expired"),
    ERR_INVALID_EMAIL_FORMAT(408, "Invalid email format"),
    CAN_NOT_DELETE(409, "Can not delete"),
    CONFLICT(409, "Conflict"),
    INVALID_FILE(410, "Invalid file"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    INVALID_VERIFIED_CODE(409, "Invalid verified code"),
    MUlTIPLE_REQUEST_CODE(410, "You are request the authen code to fast");


    private final int code;
    private final String description;

    private RestAPIStatus(int s, String v) {
        code = s;
        description = v;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RestAPIStatus getEnum(int code) {
        for (RestAPIStatus v : values())
            if (v.getCode() == code) return v;
        throw new IllegalArgumentException();
    }
}
