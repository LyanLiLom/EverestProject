package com.example.everest.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile multipartFile);
    Resource loadFile(String fileName);
}
