package com.learn.techplatform.cloudinary;

import com.learn.techplatform.cloudinary.modal.CloudinaryDeleteResponse;
import com.learn.techplatform.cloudinary.modal.CloudinaryUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    CloudinaryUploadResponse uploadImage(MultipartFile file, String folder);

    CloudinaryDeleteResponse deleteImage(String publicId);
}
