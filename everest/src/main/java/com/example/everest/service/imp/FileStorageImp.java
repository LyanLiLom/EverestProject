package com.example.everest.service.imp;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageImp {
    String storeFile(MultipartFile multipartFile);
}
