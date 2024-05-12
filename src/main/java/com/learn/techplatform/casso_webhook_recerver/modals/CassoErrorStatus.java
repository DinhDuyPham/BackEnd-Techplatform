package com.learn.techplatform.casso_webhook_recerver.modals;

public enum CassoErrorStatus {
    SUCCESS(0);

    private final int statusCode;

    private CassoErrorStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
