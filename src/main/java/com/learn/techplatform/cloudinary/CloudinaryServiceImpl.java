package com.learn.techplatform.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.learn.techplatform.cloudinary.modal.CloudinaryDeleteResponse;
import com.learn.techplatform.cloudinary.modal.CloudinaryUploadResponse;
import com.learn.techplatform.common.exceptions.ApplicationException;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.AppValueConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class CloudinaryServiceImpl implements CloudinaryService{
    private static Cloudinary cloudinary = null;

    public CloudinaryServiceImpl(AppValueConfigure appConfig) {
        synchronized (this) {
            if (cloudinary == null) {
                cloudinary =
                    new Cloudinary(
            "cloudinary://"
                    + appConfig.cloudinaryApiKey
                    + ":"
                    + appConfig.cloudinaryApiSecret
                    + "@"
                    + appConfig.cloudinaryCloudName);
            }
        }
    }

    @Override
    public CloudinaryUploadResponse uploadImage(MultipartFile file, String folder) {
        try {
            return new CloudinaryUploadResponse(
                    cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", folder)));
        } catch (IOException e) {
            throw new ApplicationException(RestAPIStatus.INTERNAL_SERVER_ERROR, RestStatusMessage.UPLOAD_IMAGE_FAILED);
        } catch (Exception e) {
            log.error("Error while uploading image {}", e.getMessage());
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_FORMAT_OR_SIZE);
        }
    }

    @Override
    public CloudinaryDeleteResponse deleteImage(String publicId) {
        try {
            return new CloudinaryDeleteResponse(
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap()));
        } catch (Exception e) {
            return null;
        }
    }
}
