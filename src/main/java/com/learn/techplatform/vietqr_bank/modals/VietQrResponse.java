package com.learn.techplatform.vietqr_bank.modals;

import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VietQrResponse {
    private String code;
    private String desc;
    private VietQrDataResponse data;

    public VietQrResponse(JSONObject jsonObject){
        this.code = jsonObject.get("code").toString();
        this.desc = jsonObject.get("desc").toString();
        String dataJson = jsonObject.get("data").toString();
        JSONObject dataObj = new JSONObject(dataJson);
        this.data = new VietQrDataResponse();
        this.data.setQrCode(dataObj.get("qrCode").toString());
        this.data.setQrDataURL(dataObj.get("qrDataURL").toString());
    }

}
