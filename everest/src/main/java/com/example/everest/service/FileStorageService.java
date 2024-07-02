package com.example.everest.service;

import com.example.everest.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService implements FileStorageServiceImp {
    @Value("${upload.file.path}")
    private String uploadDirectory;

    public String storeFile(MultipartFile file) {
        Path path = Paths.get(uploadDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = path.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
