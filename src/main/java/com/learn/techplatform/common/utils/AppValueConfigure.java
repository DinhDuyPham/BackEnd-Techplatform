package com.learn.techplatform.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppValueConfigure {

    @Value(value = "${jwt.expiration}")
    public long JWT_EXPIRATION;

    @Value(value = "${jwt.refresh_expiration}")
    public long JWT_REFRESH_EXPIRATION;

    @Value("${jwt.secret}")
    public String SECRET;

    @Value("${app.name}")
    public String APP_NAME;

    @Value("${app.api.version}")
    public String APP_API_VERSION;

    @Value("${cloudinary.cloud-name}")
    public String cloudinaryCloudName;

    @Value("${cloudinary.api-key}")
    public String cloudinaryApiKey;

    @Value("${cloudinary.api-secret}")
    public String cloudinaryApiSecret;

    @Value("${cloudinary.url}")
    public String cloudinaryUrl;

    @Value("${cloudinary.path-image-upload}")
    public String cloudinaryPathImageUpload;

    @Value("${vietqr.client-id}")
    public String vietQrClientId;

    @Value("${vietqr.api-key}")
    public String getVietQrApikey;

    @Value("${vietqr.api-url}")
    public String vietQrApiUrl;

    @Value("${bank.account-no}")
    public String bankAccountNo;

    @Value("${bank.account-name}")
    public String bankAccountName;

    @Value("${bank.acq-id}")
    public String bankAcqId;

    @Value("${firebase.project-id}")
    public String firebaseProjectId;

    @Value("${firebase.credentials.filename}")
    public String firebaseCredentialsFilename;
}
