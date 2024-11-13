package com.example.everest.controller;

import com.example.everest.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/file")
public class FileStoregeController {
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> getFile(@PathVariable String fileName){
        fileName = URLDecoder.decode(fileName,StandardCharsets.UTF_8);
        Resource resource = fileStorageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        fileName + "\"").body(resource);
    }
}
