package com.example.everest.controller;

import com.example.everest.dto.ProductDTO;
import com.example.everest.entity.ProductEntity;
import com.example.everest.payload.request.ProductRequest;
import com.example.everest.payload.response.BaseResponse;
import com.example.everest.service.ProductImageService;
import com.example.everest.service.imp.ProductImageServiceImp;
import com.example.everest.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductImageServiceImp productImageServiceImp;



    @PostMapping("/table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> tableProductForAdmin(@RequestHeader(name = "Authorization") String token){
        try {
            String role = "{\"name\":\"ROLE_ADMIN\"}";
            List<ProductDTO> list = productImageServiceImp.getAllProduct(token,role);
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
            productImageServiceImp.insertProduct(productRequest);
            return new ResponseEntity<>("Product inserted successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProductForAdmin(@Valid @ModelAttribute ProductRequest productRequest){
        try {
            productImageServiceImp.updateProduct(productRequest);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.UPGRADE_REQUIRED);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
