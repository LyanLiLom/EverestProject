package com.example.everest.service.imp;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceImp {
    String storeFile(MultipartFile multipartFile);
}
