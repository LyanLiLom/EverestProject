package com.example.everest.service;

import com.example.everest.entity.ProductEntity;
import com.example.everest.entity.ProductImageEntity;
import com.example.everest.repository.ProductImageRepository;
import com.example.everest.repository.ProductRepository;
import com.example.everest.service.imp.ProductImageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageService implements ProductImageServiceImp {
    @Value("${upload.file.path}")
    private String uploadDirectory;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity saveProductWithImage(MultipartFile file,ProductEntity product) {

        String fileName = fileStorageService.storeFile(file);
        Path path = Paths.get(uploadDirectory);
        Path filePath = path.resolve(fileName);
        String fileDowUrl = filePath.toUri().toString();

        ProductImageEntity productImage = new ProductImageEntity();
        productImage.setImgName(fileName);
        productImage.setImgUrl(fileDowUrl);

        ProductImageEntity saveImage = productImageRepository.save(productImage);

        product.setProductImage(saveImage);

        return productRepository.save(product);
    }
}
