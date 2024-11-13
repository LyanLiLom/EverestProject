package com.example.everest.service;


import com.example.everest.dto.ProductDTO;
import com.example.everest.entity.ProductEntity;
import com.example.everest.payload.request.ProductRequest;

import java.util.List;

public interface ProductImageService {
    List<ProductDTO> getAllProduct(String token,String role);
    ProductDTO convertToDTO(ProductEntity product);
    void insertProduct(ProductRequest productRequest);
    void updateProduct(ProductRequest productRequest);
    void deleteProduct(int id);
}
