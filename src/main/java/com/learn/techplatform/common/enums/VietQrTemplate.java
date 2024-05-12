package com.learn.techplatform.common.enums;

public enum VietQrTemplate {
    COMPACT2("compact2"),
    COMPACT("compact"),
    QR_ONLY("qr_only"),
    PRINT("print"),
    TP_QR("cfojtIn");

    private final String type;

    private VietQrTemplate(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
