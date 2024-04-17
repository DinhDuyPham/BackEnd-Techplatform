package com.learn.techplatform.cloudinary.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryDeleteResponse {
    private String result;

    public CloudinaryDeleteResponse(Map map) {
        this.result = (String) map.get("result");
    }
}
