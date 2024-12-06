package com.example.everest.controller;

import com.example.everest.dto.ProductDTO;
import com.example.everest.payload.request.ProductRequest;
import com.example.everest.payload.response.BaseResponse;
import com.example.everest.service.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> tableProductForAdmin(@RequestHeader(name = "Authorization") String token){
        try {
            String role = "{\"name\":\"ROLE_ADMIN\"}";
            List<ProductDTO> list = productImageService.getAllProduct(token,role);
            BaseResponse baseResponse = new BaseResponse();
            if (!list.isEmpty()) {
                baseResponse.setStatusCode(200);
                baseResponse.setMessage("Success");
                baseResponse.setData(list);
            } else {
                baseResponse.setStatusCode(204);
                baseResponse.setMessage("No products found");
                baseResponse.setData(Collections.emptyList());
            }
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> insertProductForAdmin(@Valid @ModelAttribute ProductRequest productRequest){

        try {
            productImageService.insertProduct(productRequest);
            return new ResponseEntity<>("Product inserted successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProductForAdmin(@Valid @ModelAttribute ProductRequest productRequest){
        try {
            productImageService.updateProduct(productRequest);
            System.out.println("ID: " + productRequest.getId());
            System.out.println("Product Name: " + productRequest.getProductName());
            System.out.println("Old Price: " + productRequest.getOldPrice());
            System.out.println("New Price: " + productRequest.getNewPrice());
            System.out.println("SKU: " + productRequest.getSku());
            System.out.println("Description: " + productRequest.getDescription());
            System.out.println("Information: " + productRequest.getInformation());
            System.out.println("MultipartFile: " + productRequest.getMultipartFile().getOriginalFilename());

            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProductForAdmin(@PathVariable int id){
        productImageService.deleteProduct(id);
        return ResponseEntity.ok("Product delete successfully");
    }
}
