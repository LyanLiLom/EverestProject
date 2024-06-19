package com.example.everest.service.imp;


import com.example.everest.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageServiceImp {

    ProductEntity saveProductWithImage(MultipartFile file, ProductEntity product);
}
