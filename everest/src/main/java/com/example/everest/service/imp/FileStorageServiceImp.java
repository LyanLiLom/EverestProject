package com.example.everest.service.imp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImp implements com.example.everest.service.FileStorageService {
    @Value("${upload.file.path}")
    private String uploadDirectory;

    public String storeFile(MultipartFile file) {
        String uuidFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        int maxFileNameLength = 50;
        if(uuidFileName.length() > maxFileNameLength){
            uuidFileName =uuidFileName.substring(0,maxFileNameLength);
        }
        System.out.println(uuidFileName);
        Path path = Paths.get(uploadDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileName = StringUtils.cleanPath(uuidFileName);
        Path filePath = path.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public Resource loadFile(String fileName){
        try{
            Path root = Paths.get(uploadDirectory);
            Path filePath = root.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()){
                throw new RuntimeException("Không tồn tại hoặc không đọc được file: " + fileName);
            }
            return resource;
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy file hoặc file không hợp lệ. " + e.getMessage());
        }
    }
}
