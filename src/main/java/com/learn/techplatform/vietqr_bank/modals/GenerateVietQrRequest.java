package com.learn.techplatform.vietqr_bank.modals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.JsonObject;
import com.learn.techplatform.common.enums.VietQrTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateVietQrRequest {
    private String accountNo;
    private String accountName;
    private String acqId;
    private String addInfo;
    private float amount;
    private VietQrTemplate template;

    public JSONObject toJSONObject() {


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountNo", this.accountNo);
        jsonObject.put("accountName", this.accountName);
        jsonObject.put("acqId", this.acqId);
        jsonObject.put("amount", this.amount);
        jsonObject.put("template", this.template.getType());
        jsonObject.put("addInfo", this.addInfo);
        return jsonObject;
    }
}
