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
public class CloudinaryUploadResponse {
    private String publicId;
    private String folder;
    private String secureUrl;
    private String signature;
    private String format;
    private String resourceType;
    private String assetId;
    private String versionId;
    private String type;
    private Integer version;
    private String url;

    public CloudinaryUploadResponse(Map map) {
        this.publicId = (String) map.get("public_id");
        this.folder = (String) map.get("folder");
        this.secureUrl = (String) map.get("secure_url");
        this.signature = (String) map.get("signature");
        this.format = (String) map.get("format");
        this.resourceType = (String) map.get("resource_type");
        this.assetId = (String) map.get("asset_id");
        this.versionId = (String) map.get("version_id");
        this.type = (String) map.get("type");
        this.version = (Integer) map.get("version");
        this.url = (String) map.get("url");
    }
}
