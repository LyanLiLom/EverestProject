package com.example.everest.controller;

import com.example.everest.entity.ProductEntity;
import com.example.everest.payload.request.ProductRequest;
import com.example.everest.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProductController {
    @Autowired
    private ProductImageService productImageService;
    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(@RequestParam MultipartFile file,@RequestParam ProductRequest productRequest){
        ProductEntity product = new ProductEntity();
        product.setName(productRequest.getProductName());
        product.setOldPrice(productRequest.getOldPrice());
        product.setNewPrice(productRequest.getNewPrice());
        product.setDescription(productRequest.getDescription());
        product.setInformation(productRequest.getInformation());
        product.setSku(productRequest.getSku());
        ProductEntity productSave =  productImageService.saveProductWithImage(file,product);
        return new ResponseEntity<>(productSave,HttpStatus.CREATED);
    }
}
